package com.smartpool.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartpool.model.Models.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DataService that talks to the Spring Boot backend.
 * Adapter: it translates the backend JSON into the frontend model classes.
 * The constructor loads everything once and throws IllegalStateException if the backend is down.
 */
public final class RestDataService implements DataService {
    private final String baseUrl;
    private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
    private final List<Pool> pools = new ArrayList<>();
    private final List<Camera> cameras = new ArrayList<>();
    private final List<Integer> weekly = new ArrayList<>();
    private final ObservableList<SecurityAlert> alerts =
            FXCollections.observableArrayList(a -> new Observable[] { a.status });
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final List<Long> userIds = new ArrayList<>();

    public RestDataService(String baseUrl) {
        this.baseUrl = baseUrl;
        for (JsonElement e : request("GET", "/pools", null).getAsJsonArray()) pools.add(toPool(e.getAsJsonObject()));
        for (JsonElement e : request("GET", "/cameras", null).getAsJsonArray()) cameras.add(toCamera(e.getAsJsonObject()));
        for (JsonElement e : request("GET", "/alerts", null).getAsJsonArray()) alerts.add(toAlert(e.getAsJsonObject()));
        for (JsonElement e : request("GET", "/statistics", null).getAsJsonObject().getAsJsonArray("weeklyDetections")) {
            weekly.add(e.getAsInt());
        }
        reloadUsers();
        startAlertStream();
    }

    @Override public List<Pool> pools() { return pools; }
    @Override public List<Camera> cameras() { return cameras; }
    @Override public ObservableList<SecurityAlert> alerts() { return alerts; }
    @Override public ObservableList<User> users() { return users; }
    @Override public List<Integer> weeklyDetections() { return weekly; }
    @Override public String sourceName() { return "Connected to backend"; }

    @Override
    public AiSnapshot analyze(Camera camera) {
        try {
            JsonObject o = request("GET", "/cameras/" + camera.id() + "/analysis", null).getAsJsonObject();
            return new AiSnapshot(o.get("people").getAsInt(), Risk.valueOf(text(o, "risk")),
                    o.get("confidence").getAsInt(), text(o, "movement"), text(o, "detection"));
        } catch (RuntimeException e) {
            return new AiSnapshot(0, Risk.LOW, 0, "Unavailable", "Backend error");
        }
    }

    @Override
    public SecurityAlert createCriticalAlert() {
        SecurityAlert alert = toAlert(request("POST", "/alerts/simulate-critical", null).getAsJsonObject());
        addIfAbsent(alert);
        return alert;
    }

    @Override
    public void updateAlertStatus(SecurityAlert alert, AlertStatus status) {
        String action = switch (status) {
            case CONFIRMED -> "confirm";
            case ATTENDED -> "attend";
            case CLOSED -> "close";
            default -> throw new IllegalArgumentException("Unsupported status " + status);
        };
        JsonObject result = request("POST", "/alerts/" + alert.id + "/" + action, null).getAsJsonObject();
        alert.status.set(AlertStatus.valueOf(text(result, "status")));
    }

    @Override
    public void addObservation(SecurityAlert alert, String text) {
        JsonObject body = new JsonObject();
        body.addProperty("text", text);
        JsonObject result = request("POST", "/alerts/" + alert.id + "/observations", body.toString()).getAsJsonObject();
        alert.observations.clear();
        for (JsonElement e : result.getAsJsonArray("observations")) alert.observations.add(e.getAsString());
    }

    @Override
    public void addUser(User user) {
        request("POST", "/users", userJson(user));
        reloadUsers();
    }

    @Override
    public void updateUser(int index, User user) {
        long id = userIds.get(index);
        boolean activeChanged = users.get(index).active() != user.active();
        request("PUT", "/users/" + id, userJson(user));
        if (activeChanged) request("PATCH", "/users/" + id + "/active?value=" + user.active(), null);
        reloadUsers();
    }

    @Override
    public void removeUser(int index) {
        request("DELETE", "/users/" + userIds.get(index), null);
        reloadUsers();
    }

    // ---------- HTTP ----------

    private JsonElement request(String method, String path, String body) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(baseUrl + path)).timeout(Duration.ofSeconds(5));
            if (body == null) {
                builder.method(method, HttpRequest.BodyPublishers.noBody());
            } else {
                builder.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString(body));
            }
            HttpResponse<String> response = http.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new IllegalStateException(errorMessage(response.body(), response.statusCode()));
            }
            return response.body().isBlank() ? JsonNull.INSTANCE : JsonParser.parseString(response.body());
        } catch (IOException e) {
            throw new IllegalStateException("Backend not reachable");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Request interrupted");
        }
    }

    private static String errorMessage(String body, int status) {
        try {
            JsonObject o = JsonParser.parseString(body).getAsJsonObject();
            if (o.has("message")) return o.get("message").getAsString();
        } catch (RuntimeException ignored) {
            // not JSON: use the generic message below
        }
        return "Backend error (HTTP " + status + ")";
    }

    // ---------- Real-time alerts (Server-Sent Events) ----------

    private void startAlertStream() {
        Thread thread = new Thread(this::readAlertStream, "alert-stream");
        thread.setDaemon(true);
        thread.start();
    }

    private void readAlertStream() {
        while (true) {
            try {
                HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/alerts/stream"))
                        .header("Accept", "text/event-stream").GET().build();
                HttpResponse<Stream<String>> response = http.send(request, HttpResponse.BodyHandlers.ofLines());
                response.body().forEach(line -> {
                    if (line.startsWith("data:")) {
                        SecurityAlert alert = toAlert(JsonParser.parseString(line.substring(5).trim()).getAsJsonObject());
                        Platform.runLater(() -> addIfAbsent(alert));
                    }
                });
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                // connection lost: wait and reconnect
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void addIfAbsent(SecurityAlert alert) {
        if (alerts.stream().noneMatch(a -> a.id.equals(alert.id))) {
            alerts.add(0, alert);
        }
    }

    private void reloadUsers() {
        List<User> loaded = new ArrayList<>();
        userIds.clear();
        for (JsonElement e : request("GET", "/users", null).getAsJsonArray()) {
            JsonObject o = e.getAsJsonObject();
            userIds.add(o.get("id").getAsLong());
            loaded.add(toUser(o));
        }
        users.setAll(loaded);
    }

    // ---------- JSON to model ----------

    private static String text(JsonObject o, String key) {
        JsonElement e = o.get(key);
        return e == null || e.isJsonNull() ? "" : e.getAsString();
    }

    private static Pool toPool(JsonObject o) {
        return new Pool(text(o, "name"), text(o, "location"), o.get("cameras").getAsInt(),
                PoolStatus.valueOf(text(o, "status")), o.get("people").getAsInt(), Risk.valueOf(text(o, "risk")));
    }

    private static Camera toCamera(JsonObject o) {
        return new Camera(text(o, "id"), text(o, "pool"), text(o, "location"), CameraStatus.valueOf(text(o, "status")),
                text(o, "monitoring"), text(o, "resolution"), text(o, "lastUpdate"));
    }

    private static User toUser(JsonObject o) {
        return new User(text(o, "name"), text(o, "email"), Role.valueOf(text(o, "role")), o.get("active").getAsBoolean());
    }

    private static SecurityAlert toAlert(JsonObject o) {
        SecurityAlert alert = new SecurityAlert(text(o, "id"), text(o, "date"), text(o, "time"), text(o, "pool"),
                text(o, "camera"), Risk.valueOf(text(o, "risk")), text(o, "description"),
                AlertStatus.valueOf(text(o, "status")));
        for (JsonElement e : o.getAsJsonArray("observations")) alert.observations.add(e.getAsString());
        return alert;
    }

    private static String userJson(User u) {
        JsonObject o = new JsonObject();
        o.addProperty("name", u.name());
        o.addProperty("email", u.email());
        o.addProperty("role", u.role().name());
        o.addProperty("active", u.active());
        return o.toString();
    }
}

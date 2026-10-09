package com.smartpool.service;

import static com.smartpool.model.Models.AlertStatus.*;
import static com.smartpool.model.Models.CameraStatus.*;
import static com.smartpool.model.Models.PoolStatus.*;
import static com.smartpool.model.Models.Role.*;

import com.smartpool.model.Models.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Singleton with demonstration data. All values are simulated. */
public final class MockDataService implements DataService {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final MockDataService INSTANCE = new MockDataService();

    public static MockDataService getInstance() {
        return INSTANCE;
    }

    private final List<Pool> pools = List.of(
            new Pool("Blue Wave Pool", "North Wing · Level 1", 3, NORMAL, 14, Risk.LOW),
            new Pool("Aqua Club", "Fitness Center", 2, OBSERVATION, 9, Risk.MEDIUM),
            new Pool("Sunset Lagoon", "Resort Garden", 2, ALERT, 21, Risk.HIGH),
            new Pool("Kids Splash", "Family Area", 2, NORMAL, 17, Risk.LOW),
            new Pool("Olympic Arena", "University Campus", 2, NORMAL, 6, Risk.LOW),
            new Pool("Hotel Terrace", "Rooftop · Level 12", 1, EMERGENCY, 5, Risk.CRITICAL));

    private final List<Camera> cameras = List.of(
            cam(1, "Blue Wave Pool", "Shallow end", ONLINE, "1080p"),
            cam(2, "Blue Wave Pool", "Deep end", ONLINE, "1080p"),
            cam(3, "Blue Wave Pool", "Entrance", OFFLINE, "720p"),
            cam(4, "Aqua Club", "Lane area", ONLINE, "1080p"),
            cam(5, "Aqua Club", "Pool deck", MAINTENANCE, "720p"),
            cam(6, "Sunset Lagoon", "East side", ONLINE, "4K"),
            cam(7, "Sunset Lagoon", "West side", ONLINE, "1080p"),
            cam(8, "Kids Splash", "Wading area", ONLINE, "1080p"),
            cam(9, "Kids Splash", "Slides", ERROR, "1080p"),
            cam(10, "Olympic Arena", "Main pool", ONLINE, "4K"),
            cam(11, "Olympic Arena", "Diving area", ONLINE, "1080p"),
            cam(12, "Hotel Terrace", "Rooftop", ONLINE, "1080p"));

    // The extractor makes the list fire an event when an alert status changes.
    private final ObservableList<SecurityAlert> alerts =
            FXCollections.observableArrayList(a -> new Observable[] { a.status });

    private final ObservableList<User> users = FXCollections.observableArrayList(
            new User("Laura Mendez", "laura.mendez@smartpool.demo", ADMINISTRATOR, true),
            new User("Carlos Rojas", "carlos.rojas@smartpool.demo", SUPERVISOR, true),
            new User("Ana Torres", "ana.torres@smartpool.demo", OPERATOR, true),
            new User("David Silva", "david.silva@smartpool.demo", OPERATOR, true),
            new User("Marta Ruiz", "marta.ruiz@smartpool.demo", SUPERVISOR, false),
            new User("Pedro Gil", "pedro.gil@smartpool.demo", OPERATOR, false));

    private int nextId = 1001;

    private MockDataService() {
        // Oldest first: every new alert is inserted at the top of the list.
        addAlert(4, "18:55:44", "Hotel Terrace", "CAM-012", Risk.CRITICAL, "Possible drowning pattern detected", CLOSED);
        addAlert(3, "11:03:09", "Sunset Lagoon", "CAM-007", Risk.LOW, "Camera view partially obstructed", CLOSED);
        addAlert(2, "15:36:27", "Olympic Arena", "CAM-010", Risk.HIGH, "Prolonged submersion detected", CLOSED);
        addAlert(1, "12:10:51", "Blue Wave Pool", "CAM-002", Risk.LOW, "Crowding detected in shallow end", CLOSED);
        addAlert(1, "17:22:05", "Kids Splash", "CAM-008", Risk.MEDIUM, "Child detected alone near pool edge", ATTENDED);
        addAlert(0, "08:47:10", "Aqua Club", "CAM-004", Risk.MEDIUM, "Swimmer outside normal lane pattern", NEW);
        addAlert(0, "09:05:33", "Sunset Lagoon", "CAM-006", Risk.HIGH, "Irregular movement detected in deep area", NEW);
        addAlert(0, "09:41:12", "Hotel Terrace", "CAM-012", Risk.CRITICAL, "Swimmer motionless underwater for several seconds", CONFIRMED);
    }

    private static Camera cam(int n, String pool, String location, CameraStatus status, String resolution) {
        boolean online = status == ONLINE;
        return new Camera(String.format("CAM-%03d", n), pool, location, status,
                online ? "AI Active" : "Paused", resolution, online ? "Just now" : "12 min ago");
    }

    private SecurityAlert addAlert(int daysAgo, String time, String pool, String camera,
                                   Risk risk, String text, AlertStatus status) {
        SecurityAlert alert = new SecurityAlert("AL-" + nextId++, LocalDate.now().minusDays(daysAgo).toString(),
                time, pool, camera, risk, text, status);
        alerts.add(0, alert);
        return alert;
    }

    @Override public List<Pool> pools() { return pools; }
    @Override public List<Camera> cameras() { return cameras; }
    @Override public ObservableList<SecurityAlert> alerts() { return alerts; }
    @Override public ObservableList<User> users() { return users; }
    @Override public List<Integer> weeklyDetections() { return List.of(42, 57, 38, 64, 71, 55, 48); }
    @Override public void updateAlertStatus(SecurityAlert alert, AlertStatus status) { alert.status.set(status); }
    @Override public void addObservation(SecurityAlert alert, String text) { alert.observations.add(text); }
    @Override public void addUser(User user) { users.add(user); }
    @Override public void updateUser(int index, User user) { users.set(index, user); }
    @Override public void removeUser(int index) { users.remove(index); }
    @Override public String sourceName() { return "Demo mode"; }

    @Override
    public AiSnapshot analyze(Camera camera) {
        if (camera.status() != ONLINE) {
            return new AiSnapshot(0, Risk.LOW, 0, "No data", "Offline");
        }
        int h = Math.abs(camera.id().hashCode());
        int level = h % 3;
        String[] movement = { "Normal swimming", "Active play", "Irregular movement" };
        return new AiSnapshot(1 + h % 6, Risk.values()[level], 72 + h % 26, movement[level], "Monitoring");
    }

    @Override
    public SecurityAlert createCriticalAlert() {
        Pool pool = pools.get(nextId % pools.size());
        String camera = cameras.stream()
                .filter(c -> c.pool().equals(pool.name()) && c.status() == ONLINE)
                .map(Camera::id).findFirst().orElse("CAM-001");
        return addAlert(0, LocalTime.now().format(TIME), pool.name(), camera, Risk.CRITICAL,
                "Possible drowning detected (simulated)", NEW);
    }
}

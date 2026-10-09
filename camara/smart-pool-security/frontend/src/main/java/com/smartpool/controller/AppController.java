package com.smartpool.controller;

import com.smartpool.components.*;
import com.smartpool.model.Models.Risk;
import com.smartpool.model.Models.SecurityAlert;
import com.smartpool.service.DataService;
import com.smartpool.ui.*;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.animation.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/** Builds the application shell, handles navigation, logout and critical alert reactions. */
public final class AppController {
    private final DataService data;
    private final Map<String, Supplier<Node>> screens = new LinkedHashMap<>();
    private final Map<String, Node> cache = new HashMap<>();
    private final ScrollPane scroll = new ScrollPane();
    private final Sidebar sidebar = new Sidebar(this::navigate);
    private StackPane shell;
    private Scene scene;

    public AppController(DataService data) {
        this.data = data;
        screens.put("Dashboard", () -> new DashboardView(data));
        screens.put("Pools", () -> new PoolsView(data));
        screens.put("Cameras", () -> new CamerasView(data));
        screens.put("Alerts", () -> new AlertsView(data));
        screens.put("Incidents", () -> new IncidentsView(data));
        screens.put("Statistics", () -> new StatisticsView(data));
        screens.put("Users", () -> new UsersView(data));
        screens.put("Settings", () -> new SettingsView(data));
    }

    public Scene createScene() {
        Header header = new Header(data, this::navigate, this::logout);
        CriticalBanner banner = new CriticalBanner();
        Node toasts = ToastManager.getInstance().getHost();

        scroll.setFitToWidth(true);
        scroll.setBackground(Theme.bg(Theme.BG, 0));
        scroll.setBorder(Border.EMPTY);

        StackPane center = new StackPane(scroll, toasts, banner);
        StackPane.setAlignment(toasts, Pos.TOP_RIGHT);
        StackPane.setAlignment(banner, Pos.TOP_CENTER);
        StackPane.setMargin(banner, new Insets(18));

        BorderPane root = new BorderPane(center);
        root.setTop(header);
        root.setLeft(sidebar);
        root.setBackground(Theme.bg(Theme.BG, 0));

        shell = new StackPane(root, loadingOverlay());
        scene = new Scene(shell, 1366, 820, Theme.BG);

        // Observer: react whenever a critical alert is added to the list.
        data.alerts().addListener((ListChangeListener<SecurityAlert>) c -> {
            while (c.next()) {
                if (!c.wasAdded()) continue;
                for (SecurityAlert a : c.getAddedSubList()) {
                    if (a.risk == Risk.CRITICAL) {
                        banner.show(a);
                        ToastManager.getInstance().show("Critical alert created: " + a.id, Theme.RED);
                    }
                }
            }
        });

        // Demo: a simulated critical alert appears after 25 seconds.
        PauseTransition demo = new PauseTransition(Duration.seconds(25));
        demo.setOnFinished(e -> ToastManager.getInstance().attempt(data::createCriticalAlert, null, Theme.RED));
        demo.play();

        navigate("Dashboard");
        return scene;
    }

    /** Called after the stage is visible (the scroll viewport exists only then). */
    public void onShown() {
        Node viewport = scroll.lookup(".viewport");
        if (viewport instanceof Region region) {
            region.setBackground(Theme.bg(Theme.BG, 0));
        }
    }

    private void navigate(String name) {
        sidebar.select(name);
        Node page = cache.computeIfAbsent(name, key -> screens.get(key).get());
        scroll.setContent(page);
        scroll.setVvalue(0);
        Anim.fadeIn(page, 350);
        Anim.slideIn(page, 0, 24, 350);
    }

    private void logout() {
        Button signIn = Btn.create("Sign in again", Theme.BLUE, () -> scene.setRoot(shell));
        VBox box = new VBox(16, Theme.text("Signed out", 30, Theme.TEXT, true),
                Theme.text("Your demo session has ended.", 14, Theme.MUTED, false), signIn);
        box.setAlignment(Pos.CENTER);
        box.setBackground(Theme.bg(Theme.BG, 0));
        scene.setRoot(box);
        Anim.fadeIn(box, 400);
    }

    private Node loadingOverlay() {
        Circle ring = new Circle(26);
        ring.setFill(null);
        ring.setStroke(Theme.CYAN);
        ring.setStrokeWidth(5);
        ring.getStrokeDashArray().addAll(120.0, 44.0);
        RotateTransition spin = new RotateTransition(Duration.seconds(1), ring);
        spin.setByAngle(360);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setInterpolator(Interpolator.LINEAR);
        spin.play();

        VBox box = new VBox(20, new StackPane(ring), Theme.text("Starting Smart Pool Security...", 14, Theme.MUTED, true));
        box.setAlignment(Pos.CENTER);
        box.setBackground(Theme.bg(Theme.BG, 0));

        PauseTransition wait = new PauseTransition(Duration.millis(1500));
        wait.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(500), box);
            fade.setToValue(0);
            fade.setOnFinished(x -> shell.getChildren().remove(box));
            fade.play();
        });
        wait.play();
        return box;
    }
}

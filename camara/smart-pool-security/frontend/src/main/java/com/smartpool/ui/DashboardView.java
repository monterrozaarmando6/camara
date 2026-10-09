package com.smartpool.ui;

import com.smartpool.components.Card;
import com.smartpool.components.PoolCard;
import com.smartpool.components.StatCard;
import com.smartpool.model.Models.*;
import com.smartpool.service.DataService;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public final class DashboardView extends Page {
    private final DataService data;
    private final VBox body = new VBox(22);

    public DashboardView(DataService data) {
        super("Dashboard", "Overview of pools, cameras and AI detections");
        this.data = data;
        getChildren().add(body);
        data.alerts().addListener((ListChangeListener<SecurityAlert>) c -> refresh());
        refresh();
    }

    private void refresh() {
        List<Pool> pools = data.pools();
        long normal = pools.stream().filter(p -> p.status() == PoolStatus.NORMAL).count();
        long online = data.cameras().stream().filter(c -> c.status() == CameraStatus.ONLINE).count();
        long fresh = data.alerts().stream().filter(a -> a.status.get() == AlertStatus.NEW).count();
        long open = data.alerts().stream().filter(a -> a.status.get() != AlertStatus.CLOSED).count();
        long critical = data.alerts().stream()
                .filter(a -> a.risk == Risk.CRITICAL && a.status.get() != AlertStatus.CLOSED).count();

        StatCard criticalCard = new StatCard("🚨", "Critical Incidents", (int) critical,
                critical > 0 ? "Immediate attention" : "All clear", "Simulated data", Theme.RED);
        if (critical > 0) Anim.pulse(criticalCard, Theme.RED);

        HBox stats = new HBox(18,
                new StatCard("🏊", "Active Pools", pools.size(), normal + " Normal", (pools.size() - normal) + " Need attention", Theme.CYAN),
                new StatCard("📷", "Active Cameras", (int) online, online + " Online", (data.cameras().size() - online) + " Offline / issues", Theme.GREEN),
                new StatCard("⚠", "Active Alerts", (int) open, fresh + " New", (open - fresh) + " In progress", Theme.ORANGE),
                criticalCard);
        for (Node n : stats.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        FlowPane poolFlow = new FlowPane(18, 18);
        pools.forEach(p -> poolFlow.getChildren().add(new PoolCard(p)));

        Card recent = new Card(Theme.BLUE);
        recent.getChildren().add(Theme.text("Recent alerts", 17, Theme.TEXT, true));
        data.alerts().stream().limit(4).forEach(a -> {
            HBox row = new HBox(14, Theme.text(a.id, 14, Theme.CYAN, true), Theme.text(a.pool, 14, Theme.TEXT, false),
                    Theme.text(a.camera, 13, Theme.MUTED, false), Theme.spacer(),
                    Theme.badge(a.risk.name(), Theme.of(a.risk)),
                    Theme.badge(Theme.pretty(a.status.get()), Theme.of(a.status.get())));
            row.setAlignment(Pos.CENTER_LEFT);
            recent.getChildren().add(row);
        });

        body.getChildren().setAll(stats, Theme.section("Pool Monitoring"), poolFlow, recent);
    }
}

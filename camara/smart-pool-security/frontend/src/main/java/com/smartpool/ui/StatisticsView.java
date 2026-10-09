package com.smartpool.ui;

import com.smartpool.components.AnimatedBar;
import com.smartpool.components.Card;
import com.smartpool.components.StatCard;
import com.smartpool.model.Models.*;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import java.util.Collections;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class StatisticsView extends Page {

    public StatisticsView(DataService data) {
        super("System Statistics", "Aggregated metrics (demo data)");
        List<Integer> weekly = data.weeklyDetections();
        int detections = weekly.stream().mapToInt(Integer::intValue).sum();
        int total = data.alerts().size();
        long critical = data.alerts().stream().filter(a -> a.risk == Risk.CRITICAL).count();
        long resolved = data.alerts().stream().filter(a -> a.status.get() == AlertStatus.CLOSED).count();
        long online = data.cameras().stream().filter(c -> c.status() == CameraStatus.ONLINE).count();
        long normalPools = data.pools().stream().filter(p -> p.status() == PoolStatus.NORMAL).count();

        FlowPane summary = new FlowPane(18, 18,
                new StatCard("🎯", "Total detections", detections, "This week", "AI demo data", Theme.CYAN),
                new StatCard("⚠", "Total alerts", total, "All time", "Demo data", Theme.ORANGE),
                new StatCard("🚨", "Critical alerts", (int) critical, "Highest risk", "Demo data", Theme.RED),
                new StatCard("✅", "Resolved incidents", (int) resolved, "Closed", (total - resolved) + " still open", Theme.GREEN),
                new StatCard("📷", "Active cameras", (int) online, "of " + data.cameras().size() + " cameras", "Online now", Theme.BLUE),
                new StatCard("🏊", "Pool status", data.pools().size(), normalPools + " normal", (data.pools().size() - normalPools) + " need attention", Theme.CYAN));

        Card weeklyCard = new Card(Theme.CYAN);
        weeklyCard.setPrefWidth(440);
        weeklyCard.getChildren().addAll(Theme.text("Detections per day (this week)", 16, Theme.TEXT, true), weeklyBars(weekly));

        Card riskCard = new Card(Theme.ORANGE);
        riskCard.setPrefWidth(440);
        riskCard.getChildren().add(Theme.text("Alerts by risk level", 16, Theme.TEXT, true));
        for (Risk r : Risk.values()) {
            long n = data.alerts().stream().filter(a -> a.risk == r).count();
            riskCard.getChildren().add(barRow(Theme.pretty(r), n, total, Theme.of(r)));
        }

        Card poolCard = new Card(Theme.GREEN);
        poolCard.setPrefWidth(440);
        poolCard.getChildren().add(Theme.text("Pool monitoring status", 16, Theme.TEXT, true));
        for (PoolStatus s : PoolStatus.values()) {
            long n = data.pools().stream().filter(p -> p.status() == s).count();
            poolCard.getChildren().add(barRow(Theme.pretty(s), n, data.pools().size(), Theme.of(s)));
        }

        getChildren().addAll(summary, new FlowPane(18, 18, weeklyCard, riskCard, poolCard));
    }

    private HBox weeklyBars(List<Integer> values) {
        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
        int max = Collections.max(values);
        HBox bars = new HBox(14);
        bars.setAlignment(Pos.BOTTOM_CENTER);
        bars.setPrefHeight(220);
        for (int i = 0; i < values.size(); i++) {
            Region bar = new Region();
            bar.setPrefWidth(30);
            bar.setPrefHeight(0);
            bar.setMaxHeight(Region.USE_PREF_SIZE);
            bar.setBackground(Theme.bg(Theme.gradient(Theme.CYAN, Theme.BLUE), 8));
            new Timeline(new KeyFrame(Duration.millis(900),
                    new KeyValue(bar.prefHeightProperty(), 150.0 * values.get(i) / max, Interpolator.EASE_OUT))).play();
            VBox column = new VBox(6, Theme.text(String.valueOf(values.get(i)), 12, Theme.TEXT, true), bar,
                    Theme.text(days[i], 12, Theme.MUTED, false));
            column.setAlignment(Pos.BOTTOM_CENTER);
            column.setMinWidth(40);
            bars.getChildren().add(column);
        }
        return bars;
    }

    private HBox barRow(String label, long value, long max, Color color) {
        AnimatedBar bar = new AnimatedBar(color);
        bar.setValue(max == 0 ? 0 : (double) value / max);
        HBox.setHgrow(bar, Priority.ALWAYS);
        Label name = Theme.text(label, 13, Theme.TEXT, false);
        name.setMinWidth(100);
        HBox row = new HBox(12, name, bar, Theme.text(String.valueOf(value), 13, Theme.TEXT, true));
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}

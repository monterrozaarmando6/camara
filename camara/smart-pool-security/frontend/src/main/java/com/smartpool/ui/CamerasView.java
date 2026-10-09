package com.smartpool.ui;

import com.smartpool.components.AiPanel;
import com.smartpool.components.Card;
import com.smartpool.components.SimulatedFeed;
import com.smartpool.model.Models.AiSnapshot;
import com.smartpool.model.Models.Camera;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public final class CamerasView extends Page {
    private final DataService data;
    private final SimulatedFeed feed = new SimulatedFeed();
    private final AiPanel ai = new AiPanel();

    public CamerasView(DataService data) {
        super("Camera Monitoring", "Simulated live view and AI analysis (click a camera to select it)");
        this.data = data;
        feed.setMaxWidth(Double.MAX_VALUE);
        HBox live = new HBox(18, feed, ai);
        HBox.setHgrow(feed, Priority.ALWAYS);

        FlowPane grid = new FlowPane(18, 18);
        for (Camera c : data.cameras()) grid.getChildren().add(cameraCard(c));
        getChildren().addAll(live, Theme.section("Available cameras"), grid);
        select(data.cameras().get(0));
    }

    private Card cameraCard(Camera c) {
        Card card = new Card(Theme.of(c.status()));
        card.setPrefWidth(280);
        card.setCursor(Cursor.HAND);
        HBox top = new HBox(10, Theme.text("📷 " + c.id(), 16, Theme.TEXT, true), Theme.spacer(),
                Theme.badge(Theme.pretty(c.status()).toUpperCase(), Theme.of(c.status())));
        top.setAlignment(Pos.CENTER_LEFT);
        card.getChildren().addAll(top, Theme.kv("Pool", c.pool()), Theme.kv("Location", c.location()),
                Theme.kv("Monitoring", c.monitoring()), Theme.kv("Resolution", c.resolution()),
                Theme.kv("Last update", c.lastUpdate()));
        card.setOnMouseClicked(e -> select(c));
        return card;
    }

    private void select(Camera c) {
        AiSnapshot snapshot = data.analyze(c);
        feed.update(c, snapshot);
        ai.update(snapshot);
    }
}

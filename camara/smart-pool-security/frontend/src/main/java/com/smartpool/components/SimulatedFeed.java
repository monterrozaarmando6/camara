package com.smartpool.components;

import com.smartpool.model.Models.AiSnapshot;
import com.smartpool.model.Models.Camera;
import com.smartpool.model.Models.CameraStatus;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/** Fake camera view: gradient background, scan line and moving detection boxes. */
public final class SimulatedFeed extends StackPane {
    private final Label title = Theme.badge("CAMERA LIVE", Theme.RED);
    private final Label people = Theme.badge("PEOPLE DETECTED: 0", Theme.CYAN);
    private final Label risk = Theme.badge("RISK LEVEL: LOW", Theme.GREEN);
    private final Label noSignal = Theme.text("NO SIGNAL", 28, Theme.MUTED, true);
    private final Pane boxes = new Pane();

    public SimulatedFeed() {
        setMinHeight(340);
        setPrefHeight(340);
        setBackground(Theme.bg(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#06141f")), new Stop(1, Color.web("#0b3a52"))), 16));
        setBorder(Theme.border(Color.web("#22d3ee55"), 16));
        Rectangle clip = new Rectangle();
        clip.setArcWidth(32);
        clip.setArcHeight(32);
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        Rectangle scan = new Rectangle(0, 3, Color.web("#22d3ee40"));
        scan.widthProperty().bind(widthProperty());
        StackPane.setAlignment(scan, Pos.TOP_LEFT);
        TranslateTransition sweep = new TranslateTransition(Duration.seconds(3.5), scan);
        sweep.setFromY(0);
        sweep.setToY(335);
        sweep.setCycleCount(Animation.INDEFINITE);
        sweep.setInterpolator(Interpolator.LINEAR);
        sweep.play();

        for (int i = 0; i < 3; i++) {
            Rectangle box = new Rectangle(60 + i * 10, 90);
            box.setX(120 + i * 170);
            box.setY(110 + (i % 2) * 50);
            box.setFill(Color.TRANSPARENT);
            box.setStroke(Theme.CYAN);
            box.setStrokeWidth(2);
            box.setArcWidth(8);
            box.setArcHeight(8);
            TranslateTransition move = new TranslateTransition(Duration.seconds(2.5 + i), box);
            move.setByX(40 - i * 25);
            move.setByY(20);
            move.setAutoReverse(true);
            move.setCycleCount(Animation.INDEFINITE);
            move.play();
            boxes.getChildren().add(box);
        }
        boxes.setMouseTransparent(true);

        Circle rec = new Circle(5, Theme.RED);
        FadeTransition blink = new FadeTransition(Duration.millis(700), rec);
        blink.setFromValue(1);
        blink.setToValue(0.2);
        blink.setAutoReverse(true);
        blink.setCycleCount(Animation.INDEFINITE);
        blink.play();

        HBox top = new HBox(8, rec, title, Theme.spacer(), Theme.badge("AI MONITORING", Theme.CYAN));
        top.setAlignment(Pos.CENTER_LEFT);
        HBox bottom = new HBox(10, people, Theme.spacer(), risk);
        VBox bottomBox = new VBox(8, Theme.text("DEMO DATA · SIMULATED FEED", 11, Theme.MUTED, true), bottom);
        BorderPane overlay = new BorderPane();
        overlay.setPadding(new Insets(16));
        overlay.setTop(top);
        overlay.setBottom(bottomBox);
        overlay.setCenter(noSignal);
        noSignal.setVisible(false);

        getChildren().addAll(boxes, scan, overlay);
    }

    public void update(Camera camera, AiSnapshot s) {
        boolean online = camera.status() == CameraStatus.ONLINE;
        Theme.restyle(title, "CAMERA LIVE · " + camera.id() + " · " + camera.pool(), online ? Theme.RED : Theme.MUTED);
        Theme.restyle(people, "PEOPLE DETECTED: " + s.people(), Theme.CYAN);
        Theme.restyle(risk, "RISK LEVEL: " + s.risk(), Theme.of(s.risk()));
        boxes.setVisible(online);
        noSignal.setVisible(!online);
        Anim.fadeIn(this, 300);
    }
}

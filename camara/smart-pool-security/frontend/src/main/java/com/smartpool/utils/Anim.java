package com.smartpool.utils;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** Reusable animations. Everything is short and subtle on purpose. */
public final class Anim {
    private Anim() {}

    public static void fadeIn(Node n, int ms) {
        FadeTransition t = new FadeTransition(Duration.millis(ms), n);
        t.setFromValue(0);
        t.setToValue(1);
        t.play();
    }

    public static void slideIn(Node n, double fromX, double fromY, int ms) {
        n.setTranslateX(fromX);
        n.setTranslateY(fromY);
        TranslateTransition t = new TranslateTransition(Duration.millis(ms), n);
        t.setToX(0);
        t.setToY(0);
        t.setInterpolator(Interpolator.EASE_OUT);
        t.play();
    }

    public static void move(Node n, double y) {
        TranslateTransition t = new TranslateTransition(Duration.millis(150), n);
        t.setToY(y);
        t.play();
    }

    public static void press(Node n) {
        ScaleTransition t = new ScaleTransition(Duration.millis(90), n);
        t.setToX(0.93);
        t.setToY(0.93);
        t.setCycleCount(2);
        t.setAutoReverse(true);
        t.play();
    }

    /** Soft glow that breathes. Used for critical elements. */
    public static void pulse(Node n, Color color) {
        DropShadow glow = new DropShadow(10, color);
        glow.setSpread(0.15);
        n.setEffect(glow);
        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 8)),
                new KeyFrame(Duration.millis(900), new KeyValue(glow.radiusProperty(), 28)));
        t.setAutoReverse(true);
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    public static void count(IntegerProperty p, int target) {
        new Timeline(new KeyFrame(Duration.millis(900), new KeyValue(p, target, Interpolator.EASE_OUT))).play();
    }
}

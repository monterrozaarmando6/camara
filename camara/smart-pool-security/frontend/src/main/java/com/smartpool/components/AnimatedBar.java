package com.smartpool.components;

import com.smartpool.utils.Theme;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/** Progress bar drawn with plain regions; the fill animates to the given value (0..1). */
public final class AnimatedBar extends HBox {
    private final DoubleProperty progress = new SimpleDoubleProperty(0);

    public AnimatedBar(Paint fill) {
        Region bar = new Region();
        bar.setMinWidth(0);
        bar.setBackground(Theme.bg(fill, 6));
        bar.prefWidthProperty().bind(widthProperty().multiply(progress));
        getChildren().add(bar);
        setMinHeight(10);
        setPrefHeight(10);
        setMaxHeight(10);
        setMaxWidth(Double.MAX_VALUE);
        setBackground(Theme.bg(Color.web("#ffffff1c"), 6));
    }

    public void setValue(double value) {
        new Timeline(new KeyFrame(Duration.millis(900), new KeyValue(progress, value, Interpolator.EASE_OUT))).play();
    }
}

package com.smartpool.components;

import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/** Dashboard card: icon, animated number and two status lines. */
public final class StatCard extends Card {
    public StatCard(String icon, String title, int value, String line1, String line2, Color accent) {
        super(accent);
        setPrefWidth(250);
        setMinWidth(180);
        StackPane iconBox = new StackPane(Theme.text(icon, 20, Theme.TEXT, false));
        iconBox.setMinSize(44, 44);
        iconBox.setMaxSize(44, 44);
        iconBox.setBackground(Theme.bg(Theme.gradient(accent.deriveColor(0, 1, 0.7, 1), accent), 12));
        HBox top = new HBox(12, iconBox, Theme.text(title.toUpperCase(), 12, Theme.MUTED, true));
        top.setAlignment(Pos.CENTER_LEFT);

        IntegerProperty n = new SimpleIntegerProperty(0);
        Label number = Theme.text("0", 38, Theme.TEXT, true);
        number.textProperty().bind(n.asString());
        Anim.count(n, value);

        getChildren().addAll(top, number, Theme.text(line1, 13, accent, true), Theme.text(line2, 13, Theme.MUTED, false));
    }
}

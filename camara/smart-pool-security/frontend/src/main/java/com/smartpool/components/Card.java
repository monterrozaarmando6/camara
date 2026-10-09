package com.smartpool.components;

import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/** Rounded card that lifts and highlights its border on hover. */
public class Card extends VBox {
    private static final Color IDLE = Color.web("#ffffff1f");

    public Card(Color accent) {
        super(10);
        setPadding(new Insets(18));
        setBackground(Theme.bg(Theme.CARD, 16));
        setBorder(Theme.border(IDLE, 16));
        setEffect(Theme.shadow());
        setOnMouseEntered(e -> { Anim.move(this, -4); setBorder(Theme.border(accent, 16)); });
        setOnMouseExited(e -> { Anim.move(this, 0); setBorder(Theme.border(IDLE, 16)); });
    }
}

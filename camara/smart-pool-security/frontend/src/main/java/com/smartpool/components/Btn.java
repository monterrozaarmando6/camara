package com.smartpool.components;

import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Factory for animated buttons (hover color + click animation). */
public final class Btn {
    private Btn() {}

    public static Button create(String text, Color color, Runnable action) {
        Button b = new Button(text);
        Color normal = color.deriveColor(0, 1, 0.85, 1);
        b.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        b.setTextFill(Color.WHITE);
        b.setPadding(new Insets(8, 16, 8, 16));
        b.setCursor(Cursor.HAND);
        b.setBackground(Theme.bg(normal, 10));
        b.setOnMouseEntered(e -> b.setBackground(Theme.bg(color.brighter(), 10)));
        b.setOnMouseExited(e -> b.setBackground(Theme.bg(normal, 10)));
        b.setOnAction(e -> { Anim.press(b); action.run(); });
        b.disabledProperty().addListener((o, was, now) -> b.setOpacity(now ? 0.35 : 1));
        return b;
    }
}

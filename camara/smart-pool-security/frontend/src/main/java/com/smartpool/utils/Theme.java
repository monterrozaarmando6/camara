package com.smartpool.utils;

import com.smartpool.model.Models.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Colors and small factory helpers. Styling is done with the JavaFX Java API only. */
public final class Theme {
    private Theme() {}

    public static final Color BG = Color.web("#0b1220"), PANEL = Color.web("#0f1a2f"), CARD = Color.web("#15213a"),
            TEXT = Color.web("#e8eefb"), MUTED = Color.web("#8da0c0"), BLUE = Color.web("#3b82f6"),
            CYAN = Color.web("#22d3ee"), GREEN = Color.web("#22c55e"), YELLOW = Color.web("#facc15"),
            ORANGE = Color.web("#fb923c"), RED = Color.web("#ef4444");

    public static Color of(Risk r) {
        return switch (r) { case LOW -> GREEN; case MEDIUM -> YELLOW; case HIGH -> ORANGE; case CRITICAL -> RED; };
    }

    public static Color of(PoolStatus s) {
        return switch (s) { case NORMAL -> GREEN; case OBSERVATION -> YELLOW; case ALERT -> ORANGE; case EMERGENCY -> RED; };
    }

    public static Color of(CameraStatus s) {
        return switch (s) { case ONLINE -> GREEN; case OFFLINE -> MUTED; case MAINTENANCE -> YELLOW; case ERROR -> RED; };
    }

    public static Color of(AlertStatus s) {
        return switch (s) { case NEW -> CYAN; case CONFIRMED -> ORANGE; case ATTENDED -> BLUE; case CLOSED -> MUTED; };
    }

    public static Color of(Role r) {
        return switch (r) { case ADMINISTRATOR -> CYAN; case SUPERVISOR -> BLUE; case OPERATOR -> MUTED; };
    }

    public static String pretty(Enum<?> e) {
        String n = e.name();
        return n.charAt(0) + n.substring(1).toLowerCase();
    }

    public static Background bg(Paint paint, double radius) {
        return new Background(new BackgroundFill(paint, new CornerRadii(radius), Insets.EMPTY));
    }

    public static Border border(Paint paint, double radius) {
        return new Border(new BorderStroke(paint, BorderStrokeStyle.SOLID, new CornerRadii(radius), new BorderWidths(1.2)));
    }

    public static LinearGradient gradient(Color a, Color b) {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, a), new Stop(1, b));
    }

    public static DropShadow shadow() {
        return new DropShadow(18, 0, 6, Color.rgb(0, 0, 0, 0.45));
    }

    public static Label text(String s, double size, Paint color, boolean bold) {
        Label label = new Label(s);
        label.setFont(Font.font("Segoe UI", bold ? FontWeight.BOLD : FontWeight.NORMAL, size));
        label.setTextFill(color);
        return label;
    }

    public static Label badge(String s, Color color) {
        Label label = new Label();
        restyle(label, s, color);
        return label;
    }

    /** Applies badge look (text + color) so a badge can be updated later. */
    public static void restyle(Label label, String s, Color color) {
        label.setText(s);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        label.setTextFill(color);
        label.setPadding(new Insets(3, 10, 3, 10));
        label.setBackground(bg(color.deriveColor(0, 1, 1, 0.16), 20));
        label.setBorder(border(color.deriveColor(0, 1, 1, 0.55), 20));
    }

    public static HBox kv(String key, String value) {
        return new HBox(8, text(key + ":", 12, MUTED, false), text(value, 12, TEXT, true));
    }

    public static VBox metric(String label, String value) {
        return new VBox(2, text(value, 22, TEXT, true), text(label, 11, MUTED, false));
    }

    public static Label section(String s) {
        return text(s, 18, TEXT, true);
    }

    public static Region spacer() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }
}

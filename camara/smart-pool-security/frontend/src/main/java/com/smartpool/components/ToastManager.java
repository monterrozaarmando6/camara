package com.smartpool.components;

import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** Singleton that shows small animated notifications (toasts) that can be closed. */
public final class ToastManager {
    private static final ToastManager INSTANCE = new ToastManager();

    public static ToastManager getInstance() {
        return INSTANCE;
    }

    private final VBox host = new VBox(10);

    private ToastManager() {
        host.setAlignment(Pos.TOP_RIGHT);
        host.setPadding(new Insets(16));
        host.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        host.setPickOnBounds(false);
    }

    public Node getHost() {
        return host;
    }

    public void show(String message, Color color) {
        Region stripe = new Region();
        stripe.setPrefWidth(4);
        stripe.setBackground(Theme.bg(color, 4));
        Button close = new Button("✕");
        close.setBackground(Background.EMPTY);
        close.setTextFill(Theme.MUTED);
        close.setCursor(Cursor.HAND);
        HBox toast = new HBox(12, stripe, Theme.text(message, 13, Theme.TEXT, true), close);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(10, 8, 10, 0));
        toast.setMinWidth(260);
        toast.setBackground(Theme.bg(Theme.CARD, 12));
        toast.setBorder(Theme.border(color.deriveColor(0, 1, 1, 0.5), 12));
        toast.setEffect(Theme.shadow());
        close.setOnAction(e -> dismiss(toast));
        host.getChildren().add(toast);
        Anim.slideIn(toast, 320, 0, 300);
        Anim.fadeIn(toast, 300);
        PauseTransition wait = new PauseTransition(Duration.seconds(4));
        wait.setOnFinished(e -> dismiss(toast));
        wait.play();
    }

    /** Runs an action: shows a success toast, or a red toast with the error message. */
    public void attempt(Runnable action, String successMessage, Color successColor) {
        try {
            action.run();
            if (successMessage != null) show(successMessage, successColor);
        } catch (RuntimeException e) {
            show(e.getMessage() == null ? "Action failed" : e.getMessage(), Theme.RED);
        }
    }

    private void dismiss(Node toast) {
        FadeTransition fade = new FadeTransition(Duration.millis(250), toast);
        fade.setToValue(0);
        fade.setOnFinished(e -> host.getChildren().remove(toast));
        fade.play();
    }
}

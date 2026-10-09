package com.smartpool.components;

import com.smartpool.model.Models.AlertStatus;
import com.smartpool.model.Models.SecurityAlert;
import com.smartpool.service.DataService;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import java.util.function.Consumer;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** Top bar: title, system status, notification bell and profile menu. */
public final class Header extends HBox {
    public Header(DataService data, Consumer<String> navigate, Runnable logout) {
        super(18);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(14, 24, 14, 24));
        setBackground(Theme.bg(Theme.PANEL, 0));

        Label title = Theme.text("🛡  Smart Pool Security", 22, Theme.CYAN, true);
        title.setTextFill(Theme.gradient(Theme.CYAN, Theme.BLUE));

        Circle dot = new Circle(5, Theme.GREEN);
        Anim.pulse(dot, Theme.GREEN);
        HBox status = new HBox(8, dot, Theme.text("System online · " + data.sourceName(), 12, Theme.GREEN, true));
        status.setAlignment(Pos.CENTER_LEFT);
        status.setPadding(new Insets(6, 14, 6, 14));
        status.setBackground(Theme.bg(Theme.GREEN.deriveColor(0, 1, 1, 0.12), 20));

        Label badge = Theme.text("0", 10, Color.WHITE, true);
        badge.setPadding(new Insets(1, 6, 1, 6));
        badge.setBackground(Theme.bg(Theme.RED, 10));
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        Button bell = new Button("🔔");
        bell.setBackground(Theme.bg(Color.web("#ffffff14"), 12));
        bell.setTextFill(Theme.TEXT);
        bell.setCursor(Cursor.HAND);
        bell.setOnAction(e -> {
            ToastManager.getInstance().show("You have " + badge.getText() + " new alerts", Theme.CYAN);
            navigate.accept("Alerts");
        });
        StackPane bellBox = new StackPane(bell, badge);

        Runnable refresh = () -> {
            long fresh = data.alerts().stream().filter(a -> a.status.get() == AlertStatus.NEW).count();
            badge.setText(String.valueOf(fresh));
            badge.setVisible(fresh > 0);
        };
        data.alerts().addListener((ListChangeListener<SecurityAlert>) c -> refresh.run());
        refresh.run();

        StackPane avatar = new StackPane(Theme.text("A", 15, Color.WHITE, true));
        avatar.setMinSize(36, 36);
        avatar.setMaxSize(36, 36);
        avatar.setBackground(Theme.bg(Theme.gradient(Theme.BLUE, Theme.CYAN), 18));
        HBox profile = new HBox(10, avatar,
                new VBox(0, Theme.text("Admin User", 13, Theme.TEXT, true), Theme.text("Administrator", 11, Theme.MUTED, false)),
                Theme.text("▾", 12, Theme.MUTED, false));
        profile.setAlignment(Pos.CENTER_LEFT);
        profile.setCursor(Cursor.HAND);
        MenuItem profileItem = new MenuItem("Profile");
        profileItem.setOnAction(e -> navigate.accept("Settings"));
        MenuItem logoutItem = new MenuItem("Log out");
        logoutItem.setOnAction(e -> logout.run());
        ContextMenu menu = new ContextMenu(profileItem, logoutItem);
        profile.setOnMouseClicked(e -> menu.show(profile, Side.BOTTOM, 0, 6));

        getChildren().addAll(title, Theme.spacer(), status, bellBox, profile);
    }
}

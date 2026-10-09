package com.smartpool.ui;

import com.smartpool.components.Card;
import com.smartpool.components.ToastManager;
import com.smartpool.model.Models.Role;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public final class SettingsView extends Page {
    public SettingsView(DataService data) {
        super("Settings", "Profile and application preferences");

        StackPane avatar = new StackPane(Theme.text("A", 24, Color.WHITE, true));
        avatar.setMinSize(60, 60);
        avatar.setMaxSize(60, 60);
        avatar.setBackground(Theme.bg(Theme.gradient(Theme.BLUE, Theme.CYAN), 30));
        HBox profileLine = new HBox(16, avatar, new VBox(4, Theme.text("Admin User", 18, Theme.TEXT, true),
                Theme.text("admin@smartpool.demo", 13, Theme.MUTED, false)), Theme.badge(Theme.pretty(Role.ADMINISTRATOR), Theme.CYAN));
        profileLine.setAlignment(Pos.CENTER_LEFT);
        Card profile = new Card(Theme.CYAN);
        profile.getChildren().addAll(Theme.text("Profile", 17, Theme.TEXT, true), profileLine);

        Card prefs = new Card(Theme.BLUE);
        prefs.getChildren().add(Theme.text("Preferences", 17, Theme.TEXT, true));
        for (String name : new String[] { "Sound alerts", "Desktop notifications", "Auto-refresh dashboard" }) {
            CheckBox box = new CheckBox(name);
            box.setSelected(true);
            box.setTextFill(Theme.TEXT);
            box.setOnAction(e -> ToastManager.getInstance().show("Setting updated", Theme.CYAN));
            prefs.getChildren().add(box);
        }

        Card system = new Card(Theme.GREEN);
        system.getChildren().addAll(Theme.text("System", 17, Theme.TEXT, true),
                Theme.kv("Version", "1.0.0 (university demo)"),
                Theme.kv("Data source", data.getClass().getSimpleName()),
                Theme.kv("Mode", data.sourceName()));

        for (Card c : new Card[] { profile, prefs, system }) c.setMaxWidth(640);
        getChildren().addAll(profile, prefs, system);
    }
}

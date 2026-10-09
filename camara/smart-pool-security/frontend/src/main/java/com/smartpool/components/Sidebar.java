package com.smartpool.components;

import com.smartpool.utils.Theme;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** Collapsible navigation menu. The selected item is highlighted. */
public final class Sidebar extends VBox {
    private static final String[][] ITEMS = {
            { "▦", "Dashboard" }, { "🏊", "Pools" }, { "📷", "Cameras" }, { "⚠", "Alerts" },
            { "🗂", "Incidents" }, { "📊", "Statistics" }, { "👥", "Users" }, { "⚙", "Settings" } };

    private final List<HBox> rows = new ArrayList<>();
    private final List<Label> labels = new ArrayList<>();
    private String selected = "";
    private boolean expanded = true;

    public Sidebar(Consumer<String> onSelect) {
        super(6);
        setPadding(new Insets(16, 12, 16, 12));
        setPrefWidth(230);
        setBackground(Theme.bg(Theme.PANEL, 0));

        Button toggle = new Button("☰");
        toggle.setBackground(Background.EMPTY);
        toggle.setTextFill(Theme.MUTED);
        toggle.setCursor(Cursor.HAND);
        toggle.setOnAction(e -> toggle());
        VBox.setMargin(toggle, new Insets(0, 0, 12, 0));
        getChildren().add(toggle);

        for (String[] item : ITEMS) {
            Label icon = Theme.text(item[0], 17, Theme.TEXT, false);
            icon.setMinWidth(28);
            icon.setAlignment(Pos.CENTER);
            Label label = Theme.text(item[1], 14, Theme.TEXT, true);
            label.setTextOverrun(OverrunStyle.CLIP);
            HBox row = new HBox(12, icon, label);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(11, 14, 11, 14));
            row.setCursor(Cursor.HAND);
            row.setOnMouseEntered(e -> { if (!item[1].equals(selected)) row.setBackground(Theme.bg(Color.web("#ffffff14"), 12)); });
            row.setOnMouseExited(e -> { if (!item[1].equals(selected)) row.setBackground(Background.EMPTY); });
            row.setOnMouseClicked(e -> onSelect.accept(item[1]));
            rows.add(row);
            labels.add(label);
            getChildren().add(row);
        }
    }

    public void select(String name) {
        selected = name;
        for (int i = 0; i < rows.size(); i++) {
            boolean on = ITEMS[i][1].equals(name);
            rows.get(i).setBackground(on
                    ? Theme.bg(Theme.gradient(Color.web("#2563eb"), Color.web("#06b6d4")), 12)
                    : Background.EMPTY);
        }
    }

    private void toggle() {
        expanded = !expanded;
        if (expanded) {
            labels.forEach(l -> { l.setVisible(true); l.setManaged(true); });
        }
        Timeline t = new Timeline(new KeyFrame(Duration.millis(260),
                new KeyValue(prefWidthProperty(), expanded ? 230 : 80, Interpolator.EASE_BOTH)));
        t.setOnFinished(e -> {
            if (!expanded) labels.forEach(l -> { l.setVisible(false); l.setManaged(false); });
        });
        t.play();
    }
}

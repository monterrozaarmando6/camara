package com.smartpool.ui;

import com.smartpool.components.Btn;
import com.smartpool.components.ToastManager;
import com.smartpool.model.Models.*;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public final class IncidentsView extends Page {
    private final DataService data;
    private final ComboBox<String> date, pool, camera, risk, status;
    private final VBox rows = new VBox(8);

    public IncidentsView(DataService data) {
        super("Incident History", "Filter, review and annotate past incidents");
        this.data = data;
        date = filter("All dates", data.alerts().stream().map(a -> a.date).distinct().sorted(Comparator.reverseOrder()).toList());
        pool = filter("All pools", data.pools().stream().map(Pool::name).toList());
        camera = filter("All cameras", data.cameras().stream().map(Camera::id).toList());
        risk = filter("All risks", Arrays.stream(Risk.values()).map(Theme::pretty).toList());
        status = filter("All statuses", Arrays.stream(AlertStatus.values()).map(Theme::pretty).toList());

        VBox table = new VBox(8, headerRow(), rows);
        table.setPadding(new Insets(18));
        table.setBackground(Theme.bg(Theme.CARD, 16));
        getChildren().addAll(new HBox(12, date, pool, camera, risk, status), table);
        data.alerts().addListener((ListChangeListener<SecurityAlert>) c -> refresh());
        refresh();
    }

    private ComboBox<String> filter(String all, List<String> values) {
        List<String> items = new ArrayList<>();
        items.add(all);
        items.addAll(values);
        ComboBox<String> box = new ComboBox<>(FXCollections.observableArrayList(items));
        box.getSelectionModel().selectFirst();
        box.setPrefWidth(165);
        box.setBackground(Theme.bg(Theme.CARD, 10));
        box.setBorder(Theme.border(Color.web("#ffffff2a"), 10));
        box.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setTextFill(Theme.TEXT);
            }
        });
        box.setOnAction(e -> refresh());
        return box;
    }

    private boolean matches(ComboBox<String> box, String value) {
        return box.getSelectionModel().getSelectedIndex() <= 0 || value.equals(box.getValue());
    }

    private void refresh() {
        rows.getChildren().clear();
        for (SecurityAlert a : data.alerts()) {
            if (matches(date, a.date) && matches(pool, a.pool) && matches(camera, a.camera)
                    && matches(risk, Theme.pretty(a.risk)) && matches(status, Theme.pretty(a.status.get()))) {
                rows.getChildren().add(row(a));
            }
        }
        if (rows.getChildren().isEmpty()) {
            rows.getChildren().add(Theme.text("No incidents match the filters.", 13, Theme.MUTED, false));
        }
    }

    private Node cell(Node node, double width) {
        HBox box = new HBox(node);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setMinWidth(width);
        box.setPrefWidth(width);
        box.setMaxWidth(width);
        return box;
    }

    private HBox headerRow() {
        Label description = Theme.text("DESCRIPTION", 11, Theme.MUTED, true);
        HBox.setHgrow(description, Priority.ALWAYS);
        description.setMinWidth(160);
        HBox h = new HBox(10, cell(Theme.text("INCIDENT", 11, Theme.MUTED, true), 80), cell(Theme.text("DATE", 11, Theme.MUTED, true), 100),
                cell(Theme.text("POOL", 11, Theme.MUTED, true), 140), cell(Theme.text("CAMERA", 11, Theme.MUTED, true), 80),
                cell(Theme.text("RISK", 11, Theme.MUTED, true), 90), cell(Theme.text("STATUS", 11, Theme.MUTED, true), 100),
                description, cell(Theme.text("ACTIONS", 11, Theme.MUTED, true), 270));
        h.setPadding(new Insets(0, 12, 0, 12));
        return h;
    }

    private HBox row(SecurityAlert a) {
        Label description = Theme.text(a.description, 13, Theme.TEXT, false);
        description.setMinWidth(160);
        HBox.setHgrow(description, Priority.ALWAYS);
        HBox actions = new HBox(8, Btn.create("View Details", Theme.BLUE, () -> details(a)),
                Btn.create("Add Observation", Theme.CYAN, () -> addObservation(a)));
        HBox r = new HBox(10, cell(Theme.text(a.id, 13, Theme.CYAN, true), 80), cell(Theme.text(a.date, 13, Theme.TEXT, false), 100),
                cell(Theme.text(a.pool, 13, Theme.TEXT, false), 140), cell(Theme.text(a.camera, 13, Theme.TEXT, false), 80),
                cell(Theme.badge(a.risk.name(), Theme.of(a.risk)), 90),
                cell(Theme.badge(Theme.pretty(a.status.get()), Theme.of(a.status.get())), 100),
                description, cell(actions, 270));
        r.setAlignment(Pos.CENTER_LEFT);
        r.setPadding(new Insets(10, 12, 10, 12));
        r.setBackground(Theme.bg(Color.web("#ffffff0a"), 10));
        r.setOnMouseEntered(e -> r.setBackground(Theme.bg(Color.web("#ffffff16"), 10)));
        r.setOnMouseExited(e -> r.setBackground(Theme.bg(Color.web("#ffffff0a"), 10)));
        return r;
    }

    private void details(SecurityAlert a) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Incident " + a.id);
        dialog.setHeaderText(a.id + " · " + a.pool + " · " + a.camera);
        dialog.setContentText(a.description + "\n\nRisk: " + Theme.pretty(a.risk) + "\nStatus: " + Theme.pretty(a.status.get())
                + "\nDate: " + a.date + " " + a.time + "\n\nObservations:\n"
                + (a.observations.isEmpty() ? "(none)" : String.join("\n", a.observations)));
        dialog.showAndWait();
    }

    private void addObservation(SecurityAlert a) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Observation");
        dialog.setHeaderText("Incident " + a.id);
        dialog.setContentText("Observation:");
        dialog.showAndWait().filter(s -> !s.isBlank()).ifPresent(s -> {
            ToastManager.getInstance().attempt(() -> data.addObservation(a, s), "Incident updated", Theme.CYAN);
        });
    }
}

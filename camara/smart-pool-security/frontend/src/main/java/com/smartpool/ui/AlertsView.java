package com.smartpool.ui;

import com.smartpool.components.Btn;
import com.smartpool.components.Card;
import com.smartpool.components.ToastManager;
import com.smartpool.model.Models.AlertStatus;
import com.smartpool.model.Models.Risk;
import com.smartpool.model.Models.SecurityAlert;
import com.smartpool.service.DataService;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public final class AlertsView extends Page {
    private final DataService data;
    private final VBox list = new VBox(14);

    public AlertsView(DataService data) {
        super("Security Alerts", "Review, confirm and close alerts (simulated)");
        this.data = data;
        addHeaderAction(Btn.create("＋ Simulate critical alert", Theme.RED,
                () -> ToastManager.getInstance().attempt(data::createCriticalAlert, null, Theme.RED)));
        getChildren().add(list);
        data.alerts().addListener((ListChangeListener<SecurityAlert>) c -> refresh());
        refresh();
    }

    private void refresh() {
        list.getChildren().clear();
        for (SecurityAlert a : data.alerts()) list.getChildren().add(row(a));
    }

    private Node row(SecurityAlert a) {
        Color riskColor = Theme.of(a.risk);
        AlertStatus s = a.status.get();
        Card card = new Card(riskColor);
        Label date = Theme.text(a.date + "  " + a.time, 12, Theme.MUTED, false);
        HBox top = new HBox(12, Theme.text(a.id, 17, Theme.CYAN, true), Theme.badge(a.risk.name(), riskColor),
                Theme.badge(Theme.pretty(s), Theme.of(s)), Theme.spacer(), date);
        top.setAlignment(Pos.CENTER_LEFT);
        Label description = Theme.text(a.description, 14, Theme.TEXT, false);
        description.setWrapText(true);
        HBox meta = new HBox(26, Theme.kv("Pool", a.pool), Theme.kv("Camera", a.camera));

        Button confirm = Btn.create("Confirm", Theme.BLUE, () -> update(a, AlertStatus.CONFIRMED, "Alert confirmed"));
        Button attend = Btn.create("Attend", Theme.ORANGE, () -> update(a, AlertStatus.ATTENDED, "Alert attended"));
        Button close = Btn.create("Close", Theme.GREEN, () -> update(a, AlertStatus.CLOSED, "Alert closed"));
        confirm.setDisable(s != AlertStatus.NEW);
        attend.setDisable(s == AlertStatus.ATTENDED || s == AlertStatus.CLOSED);
        close.setDisable(s == AlertStatus.CLOSED);

        card.getChildren().addAll(top, description, meta, new HBox(10, confirm, attend, close));
        if (a.risk == Risk.CRITICAL && s != AlertStatus.CLOSED) Anim.pulse(card, Theme.RED);
        return card;
    }

    private void update(SecurityAlert a, AlertStatus status, String message) {
        ToastManager.getInstance().attempt(() -> data.updateAlertStatus(a, status), message, Theme.GREEN);
    }
}

package com.smartpool.ui;

import com.smartpool.components.PoolCard;
import com.smartpool.model.Models.Pool;
import com.smartpool.model.Models.PoolStatus;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public final class PoolsView extends Page {
    public PoolsView(DataService data) {
        super("Pool Monitoring", "Status, occupancy and risk level of every pool");
        HBox legend = new HBox(10, Theme.text("Status legend:", 12, Theme.MUTED, true));
        legend.setAlignment(Pos.CENTER_LEFT);
        for (PoolStatus s : PoolStatus.values()) {
            legend.getChildren().add(Theme.badge(Theme.pretty(s), Theme.of(s)));
        }
        FlowPane flow = new FlowPane(18, 18);
        for (Pool p : data.pools()) flow.getChildren().add(new PoolCard(p));
        getChildren().addAll(legend, flow);
    }
}

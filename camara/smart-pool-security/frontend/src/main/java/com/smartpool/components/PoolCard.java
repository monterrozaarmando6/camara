package com.smartpool.components;

import com.smartpool.model.Models.Pool;
import com.smartpool.model.Models.PoolStatus;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/** Card with the state of one pool. The color follows the pool status. */
public final class PoolCard extends Card {
    public PoolCard(Pool p) {
        super(Theme.of(p.status()));
        setPrefWidth(300);
        HBox top = new HBox(10, Theme.text(p.name(), 17, Theme.TEXT, true), Theme.spacer(),
                Theme.badge(Theme.pretty(p.status()).toUpperCase(), Theme.of(p.status())));
        top.setAlignment(Pos.CENTER_LEFT);
        HBox metrics = new HBox(28, Theme.metric("Cameras", String.valueOf(p.cameras())),
                Theme.metric("People detected", String.valueOf(p.people())));
        HBox risk = new HBox(10, Theme.text("Risk level", 12, Theme.MUTED, false),
                Theme.badge(p.risk().name(), Theme.of(p.risk())));
        risk.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(top, Theme.text("📍 " + p.location(), 12, Theme.MUTED, false), metrics, risk);
        if (p.status() == PoolStatus.EMERGENCY) {
            Anim.pulse(this, Theme.RED);
        }
    }
}

package com.smartpool.components;

import com.smartpool.model.Models.SecurityAlert;
import com.smartpool.utils.Anim;
import com.smartpool.utils.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/** Pulsing red banner shown when a critical alert is created (simulation). */
public final class CriticalBanner extends VBox {
    private final Label detail = Theme.text("", 13, Color.web("#ffd9d9"), true);

    public CriticalBanner() {
        super(6);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(18, 32, 18, 32));
        setPrefWidth(540);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setBackground(Theme.bg(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#7f1d1d")), new Stop(1, Color.web("#dc2626"))), 18));
        setBorder(Theme.border(Color.web("#fecaca"), 18));
        Button ok = Btn.create("Acknowledge", Color.web("#111827"), () -> setVisible(false));
        VBox.setMargin(ok, new Insets(8, 0, 0, 0));
        getChildren().addAll(
                Theme.text("🚨 CRITICAL ALERT", 24, Color.WHITE, true),
                Theme.text("Possible drowning detected", 16, Color.WHITE, true),
                Theme.text("Immediate attention required", 14, Color.web("#ffe4e4"), false),
                detail,
                Theme.text("SIMULATION · demo data, not a real detection", 11, Color.web("#ffd0d0"), true),
                ok);
        setVisible(false);
        Anim.pulse(this, Theme.RED);
    }

    public void show(SecurityAlert alert) {
        detail.setText(alert.pool + " · " + alert.camera + " · " + alert.time);
        setVisible(true);
        Anim.fadeIn(this, 350);
        Anim.slideIn(this, 0, -60, 350);
    }
}

package com.smartpool.components;

import com.smartpool.model.Models.AiSnapshot;
import com.smartpool.utils.Theme;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** Shows the simulated AI analysis of the selected camera. */
public final class AiPanel extends Card {
    private final Label people = Theme.text("-", 14, Theme.TEXT, true);
    private final Label movement = Theme.text("-", 14, Theme.TEXT, true);
    private final Label detection = Theme.text("-", 14, Theme.TEXT, true);
    private final Label confidence = Theme.text("0%", 14, Theme.CYAN, true);
    private final Label risk = Theme.badge("LOW", Theme.GREEN);
    private final AnimatedBar bar = new AnimatedBar(Theme.gradient(Theme.BLUE, Theme.CYAN));

    public AiPanel() {
        super(Theme.CYAN);
        setPrefWidth(320);
        setMinWidth(280);
        HBox title = new HBox(10, Theme.text("🤖 AI Monitoring", 17, Theme.TEXT, true), Theme.spacer(),
                Theme.badge("DEMO DATA", Theme.YELLOW));
        title.setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(title, row("People detected", people), row("Movement analysis", movement),
                row("Risk level", risk), row("Detection status", detection),
                row("AI Confidence", confidence), bar);
    }

    private HBox row(String name, Node value) {
        HBox r = new HBox(10, Theme.text(name, 13, Theme.MUTED, false), Theme.spacer(), value);
        r.setAlignment(Pos.CENTER_LEFT);
        return r;
    }

    public void update(AiSnapshot s) {
        people.setText(String.valueOf(s.people()));
        movement.setText(s.movement());
        detection.setText(s.detection());
        confidence.setText(s.confidence() + "%");
        Theme.restyle(risk, s.risk().name(), Theme.of(s.risk()));
        bar.setValue(s.confidence() / 100.0);
    }
}

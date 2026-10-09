package com.smartpool.ui;

import com.smartpool.utils.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** Base class of every screen: title, subtitle, DEMO DATA badge and optional header actions. */
public abstract class Page extends VBox {
    private final HBox head = new HBox(14);

    protected Page(String title, String subtitle) {
        super(22);
        setPadding(new Insets(28));
        setBackground(Theme.bg(Theme.BG, 0));
        VBox titles = new VBox(4, Theme.text(title, 28, Theme.TEXT, true), Theme.text(subtitle, 13, Theme.MUTED, false));
        head.setAlignment(Pos.CENTER_LEFT);
        head.getChildren().addAll(titles, Theme.badge("DEMO DATA", Theme.YELLOW), Theme.spacer());
        getChildren().add(head);
    }

    protected void addHeaderAction(Node node) {
        head.getChildren().add(node);
    }
}

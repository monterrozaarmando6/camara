package com.smartpool.ui;

import com.smartpool.components.Btn;
import com.smartpool.components.Card;
import com.smartpool.components.ToastManager;
import com.smartpool.model.Models.Role;
import com.smartpool.model.Models.User;
import com.smartpool.service.DataService;
import com.smartpool.utils.Theme;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public final class UsersView extends Page {
    private final DataService data;
    private final VBox list = new VBox(10);

    public UsersView(DataService data) {
        super("User Management", "Administrators, supervisors and operators");
        this.data = data;
        addHeaderAction(Btn.create("＋ Add User", Theme.GREEN, this::addUser));
        getChildren().add(list);
        data.users().addListener((ListChangeListener<User>) c -> refresh());
        refresh();
    }

    private void refresh() {
        list.getChildren().clear();
        for (int i = 0; i < data.users().size(); i++) list.getChildren().add(row(i, data.users().get(i)));
    }

    private Card row(int index, User u) {
        StackPane avatar = new StackPane(Theme.text(u.name().substring(0, 1), 16, Color.WHITE, true));
        avatar.setMinSize(40, 40);
        avatar.setMaxSize(40, 40);
        avatar.setBackground(Theme.bg(Theme.gradient(Theme.BLUE, Theme.CYAN), 20));
        VBox info = new VBox(2, Theme.text(u.name(), 15, Theme.TEXT, true), Theme.text(u.email(), 12, Theme.MUTED, false));
        HBox.setHgrow(info, Priority.ALWAYS);
        Color statusColor = u.active() ? Theme.GREEN : Theme.MUTED;

        HBox line = new HBox(14, avatar, info, Theme.badge(Theme.pretty(u.role()), Theme.of(u.role())),
                Theme.badge(u.active() ? "Active" : "Inactive", statusColor),
                Btn.create("Edit", Theme.BLUE, () -> edit(index, u)),
                Btn.create(u.active() ? "Disable" : "Enable", Theme.ORANGE, () -> toggle(index, u)),
                Btn.create("Delete", Theme.RED, () -> delete(index)));
        line.setAlignment(Pos.CENTER_LEFT);
        Card card = new Card(Theme.BLUE);
        card.setPadding(new Insets(12, 18, 12, 18));
        card.getChildren().add(line);
        return card;
    }

    private void edit(int index, User u) {
        TextInputDialog dialog = new TextInputDialog(u.name());
        dialog.setTitle("Edit user");
        dialog.setHeaderText(u.email());
        dialog.setContentText("Name:");
        dialog.showAndWait().filter(s -> !s.isBlank()).ifPresent(name -> {
            ToastManager.getInstance().attempt(
                    () -> data.updateUser(index, new User(name, u.email(), u.role(), u.active())), "User updated", Theme.CYAN);
        });
    }

    private void toggle(int index, User u) {
        ToastManager.getInstance().attempt(
                () -> data.updateUser(index, new User(u.name(), u.email(), u.role(), !u.active())),
                u.active() ? "User disabled" : "User enabled", Theme.YELLOW);
    }

    private void delete(int index) {
        ToastManager.getInstance().attempt(() -> data.removeUser(index), "User deleted", Theme.RED);
    }

    private void addUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New user");
        dialog.setHeaderText("Create an operator account");
        dialog.setContentText("Name:");
        dialog.showAndWait().filter(s -> !s.isBlank()).ifPresent(name -> {
            String email = name.trim().toLowerCase().replace(' ', '.') + "@smartpool.demo";
            ToastManager.getInstance().attempt(
                    () -> data.addUser(new User(name.trim(), email, Role.OPERATOR, true)), "User created", Theme.GREEN);
        });
    }
}

package com.smartpool;

import com.smartpool.components.ToastManager;
import com.smartpool.controller.AppController;
import com.smartpool.service.DataService;
import com.smartpool.service.MockDataService;
import com.smartpool.service.RestDataService;
import com.smartpool.utils.Theme;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    /** Backend address. Change it with -Dsmartpool.api=http://host:port/api */
    private static final String API_URL = System.getProperty("smartpool.api", "http://localhost:8080/api");

    @Override
    public void start(Stage stage) {
        DataService data;
        boolean offline = false;
        try {
            data = new RestDataService(API_URL);
        } catch (RuntimeException e) {
            data = MockDataService.getInstance();   // fallback: demo data
            offline = true;
        }

        AppController controller = new AppController(data);
        stage.setTitle("Smart Pool Security");
        stage.setScene(controller.createScene());
        stage.setMinWidth(1000);
        stage.setMinHeight(640);
        stage.show();
        controller.onShown();

        if (offline) {
            PauseTransition wait = new PauseTransition(Duration.seconds(1.8));
            wait.setOnFinished(e -> ToastManager.getInstance().show("Backend not reachable. Using demo data.", Theme.YELLOW));
            wait.play();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

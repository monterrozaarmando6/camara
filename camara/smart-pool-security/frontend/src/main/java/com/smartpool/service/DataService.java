package com.smartpool.service;

import com.smartpool.model.Models.*;
import java.util.List;
import javafx.collections.ObservableList;

/** Contract between the UI and any data source (mock data or the REST backend). */
public interface DataService {
    List<Pool> pools();
    List<Camera> cameras();
    ObservableList<SecurityAlert> alerts();
    ObservableList<User> users();
    List<Integer> weeklyDetections();
    AiSnapshot analyze(Camera camera);
    SecurityAlert createCriticalAlert();

    void updateAlertStatus(SecurityAlert alert, AlertStatus status);
    void addObservation(SecurityAlert alert, String text);
    void addUser(User user);
    void updateUser(int index, User user);
    void removeUser(int index);

    /** Short text shown in the header, for example "Demo mode". */
    String sourceName();
}

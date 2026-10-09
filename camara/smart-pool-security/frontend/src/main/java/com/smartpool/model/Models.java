package com.smartpool.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/** All domain types of the application. Replace the data source, not these types. */
public final class Models {
    private Models() {}

    public enum Risk { LOW, MEDIUM, HIGH, CRITICAL }
    public enum PoolStatus { NORMAL, OBSERVATION, ALERT, EMERGENCY }
    public enum CameraStatus { ONLINE, OFFLINE, MAINTENANCE, ERROR }
    public enum AlertStatus { NEW, CONFIRMED, ATTENDED, CLOSED }
    public enum Role { ADMINISTRATOR, SUPERVISOR, OPERATOR }

    public record Pool(String name, String location, int cameras, PoolStatus status, int people, Risk risk) {}

    public record Camera(String id, String pool, String location, CameraStatus status,
                         String monitoring, String resolution, String lastUpdate) {}

    public record User(String name, String email, Role role, boolean active) {}

    /** Result of the (simulated) AI analysis of one camera. */
    public record AiSnapshot(int people, Risk risk, int confidence, String movement, String detection) {}

    /** An alert / incident. The status is a JavaFX property so the UI can observe it. */
    public static final class SecurityAlert {
        public final String id, date, time, pool, camera, description;
        public final Risk risk;
        public final ObjectProperty<AlertStatus> status;
        public final List<String> observations = new ArrayList<>();

        public SecurityAlert(String id, String date, String time, String pool, String camera,
                             Risk risk, String description, AlertStatus status) {
            this.id = id;
            this.date = date;
            this.time = time;
            this.pool = pool;
            this.camera = camera;
            this.risk = risk;
            this.description = description;
            this.status = new SimpleObjectProperty<>(status);
        }
    }
}

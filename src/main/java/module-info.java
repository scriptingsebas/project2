module org.turnbasedtitans.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.turnbasedtitans.project2.controller to javafx.fxml;

    exports org.turnbasedtitans.project2;
    exports org.turnbasedtitans.project2.database;
}
module org.turnbasedtitans.project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.turnbasedtitans.project2.controller to javafx.fxml;

    opens temp to javafx.fxml;
    exports temp;
}
module org.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.turnbasedtitans.project2 to javafx.fxml;
    exports org.turnbasedtitans.project2;
}
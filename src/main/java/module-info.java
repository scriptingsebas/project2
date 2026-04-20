module org.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.turnedbasedtitans.project2 to javafx.fxml;
    exports org.turnedbasedtitans.project2;
}
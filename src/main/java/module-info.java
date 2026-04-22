module org.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens temp to javafx.fxml;
    exports temp;
}
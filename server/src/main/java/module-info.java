module server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports frontend;
    exports backend;
    exports model;
    exports interfaces;
    opens backend to com.google.gson;
    opens frontend to com.google.gson, javafx.fxml;
}
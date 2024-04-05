module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports frontend;
    opens frontend to javafx.fxml;

    opens backend to com.google.gson;
}
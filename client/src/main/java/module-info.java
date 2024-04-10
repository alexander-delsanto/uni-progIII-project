module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports frontend;
    opens frontend to javafx.fxml;
    exports model;

    opens backend to com.google.gson;
    exports frontend.component;
    opens frontend.component to javafx.fxml;
    opens model to com.google.gson;
}
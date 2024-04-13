module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports frontend;
    opens frontend to javafx.fxml;
    exports model;

    exports interfaces;
    exports frontend.component;
    exports frontend.util;
    opens frontend.component to javafx.fxml;
    opens model to com.google.gson;
    exports model.message;
    opens model.message to com.google.gson;
}
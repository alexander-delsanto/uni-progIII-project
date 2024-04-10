package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 1000;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        // initialize the stage
        stage.setTitle("Email client");
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.setMinWidth(500);
        stage.setMinHeight(400);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

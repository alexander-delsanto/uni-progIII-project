package frontend;

import backend.Server;
import interfaces.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private int WIDTH = 800;
    private int HEIGHT = 600;
    private Stage stage;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("server-view.fxml"));
        stage.setScene(new Scene(loader.load(), WIDTH, HEIGHT));

        setupServer(loader.getController());
        setParameters();
    }

    private void setupServer(Logger logger) {
        Server server = Server.getServer(logger);

        executorService.execute(server);
        stage.setOnCloseRequest((event) -> {
            executorService.shutdown();
        });
    }

    private void setParameters(){
        stage.setTitle("Mail Server");

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.show();

        URL imageURL = getClass().getResource("images/icon.png");
        if(imageURL != null)
            stage.getIcons().add(new Image(imageURL.toExternalForm()));
        else
            System.err.println("Couldn't load logo");
    }

    public static void main(String[] args) {
        launch();
    }
}

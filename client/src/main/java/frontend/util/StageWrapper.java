package frontend.util;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.net.URL;
import javafx.stage.Window;

public class StageWrapper {
    private Stage stage;

    public StageWrapper(Stage stage, String title,int minWidth, int minHeight) {
        this.stage = stage;
        if (this.stage == null){
            this.stage = new Stage();
        }

        this.stage.setTitle(title);
        this.stage.setMinWidth(minWidth);
        this.stage.setMinHeight(minHeight);
    }

    public void setTitle(String title){ this.stage.setTitle(title); }

    public void open () { this.stage.show(); }
    public void close () { this.stage.close(); }

    public void setIcon (URL iconPath) {
        if (iconPath != null)
            stage.getIcons().add(new Image(iconPath.toExternalForm()));
        else
            System.err.println("Icon path is invalid");
    }


    public void setRoot(Parent element) {
        Scene scene = stage.getScene();

        if (scene == null) {
            stage.setScene(new Scene(element));
        } else {
            scene.setRoot(element);
        }
    }

    public <T> T setRootAndGetController(URL fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlPath);
            setRoot(loader.load());
            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void setModal(Window window) {
        if (window == null) {
            System.err.println("Window is null");
            return;
        }

        stage.initOwner(window);
        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
    }

    public void setOnCloseRequest(EventHandler<WindowEvent> handler) {
        stage.setOnCloseRequest(handler);
    }

    public void setWidth(int width) { this.stage.setWidth(width); }
    public void setHeight(int height) { this.stage.setHeight(height); }
    public void setMinWidth(int minWidth) { this.stage.setMinWidth(minWidth); }
    public void setMinHeight(int minHeight) { this.stage.setMinHeight(minHeight); }

    public Stage getStage() { return stage; }
}

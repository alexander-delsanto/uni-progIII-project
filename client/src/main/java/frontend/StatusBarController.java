package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.StatusBar;

public class StatusBarController {
    @FXML private Label userLabel;
    @FXML private Label statusLabel;
    private StatusBar statusBar;

    @FXML private void initialize() {

    }

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
        userLabel.textProperty().bind(statusBar.userProperty());
        statusLabel.textProperty().bind(statusBar.statusProperty());
    }

    public void setUser(String user) {
        statusBar.setUser(user);
    }

    public void setStatus(String status) {
        statusBar.setStatus(status);
    }
}

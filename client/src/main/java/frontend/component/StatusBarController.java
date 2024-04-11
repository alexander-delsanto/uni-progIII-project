package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.UserData;

public class StatusBarController {
    @FXML private Label userLabel;
    @FXML private Label statusLabel;
    private final UserData userData = UserData.getInstance();

    @FXML
    private void initialize() {
        userLabel.textProperty().bind(userData.userProperty());
        statusLabel.textProperty().bind(userData.statusProperty());
    }

    public void setUser(String name) {
        userData.setUser(name);
    }

    public void setStatus(String status) {
        userData.setStatus(status);
    }
}

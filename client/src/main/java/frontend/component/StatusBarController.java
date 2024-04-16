package frontend.component;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.MailBox;
import model.UserData;

public class StatusBarController {
    @FXML private Label userLabel;
    @FXML private Label statusLabel;
    private final UserData userData = UserData.getInstance();
    private final MailBox mailBox = MailBox.getInstance();

    @FXML
    private void initialize() {
        userLabel.textProperty().bind(userData.userProperty());

        statusLabel.textProperty().bind(Bindings.createStringBinding(
                () -> mailBox.onlineProperty().get() ? "Online" : "Offline",
                mailBox.onlineProperty()
        ));
        mailBox.onlineProperty().addListener((obs, oldStatus, newStatus) -> {
            statusLabel.getStyleClass().removeAll("online", "offline");
            statusLabel.getStyleClass().add(newStatus ? "online" : "offline");
        });

    }

    public void setUser(String name) {
        userData.setUser(name);
    }
}

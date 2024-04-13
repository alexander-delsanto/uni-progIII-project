package frontend;

import interfaces.EndStatusListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.EmailValidator;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private Label errorLabel;
    private final EmailValidator emailValidator = new EmailValidator();
    private EndStatusListener<String> listener = null;

    @FXML
    public void login() {
        if (listener == null) {
            System.err.println("Error");
            return;
        }

        String emailAddress = usernameField.getText();
        if (emailValidator.isAddressValid(emailAddress)) {
            listener.useEndStatus(emailAddress);
        } else {
            errorLabel.setText("Invalid email address");
        }
    }

    public void loginAsUser(EndStatusListener<String> listener) {
        this.listener = listener;
    }
}

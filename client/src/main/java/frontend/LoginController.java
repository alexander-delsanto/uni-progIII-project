package frontend;

import backend.AuthService;
import interfaces.EndStatusListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import model.EmailValidator;
import model.UserData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginController implements EndStatusListener<Pair<Boolean, String>> {
    @FXML private TextField usernameField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    private String emailAddress;
    private final EmailValidator emailValidator = new EmailValidator();
    private EndStatusListener<String> listener = null;
    private boolean isProcessing = false;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });

    @FXML
    private void requestAuth(ActionEvent event) {
        String buttonId = ((Button)event.getSource()).getId();
        emailAddress = usernameField.getText();
        if (buttonId.equals("loginButton")) {
            initiateService(new AuthService(emailAddress, true));
        } else {
            initiateService(new AuthService(emailAddress, false));
        }
    }

    private void initiateService(AuthService authService) {
        if (isProcessing || listener == null) {
            System.err.println("Listener is not set or process is already running.");
            return;
        }

        if (!emailValidator.isAddressValid(emailAddress)) {
            errorLabel.setText("Invalid email address");
            return;
        }

        isProcessing = true;
        updateUIForProcessing(true);
        try {
            authService.setEndStatusListener(this);
            executorService.execute(authService);
        } catch (Exception e) {
            errorLabel.setText("Failed to start service.");
            isProcessing = false;
            updateUIForProcessing(false);
        }

    }

    private void updateUIForProcessing(boolean isProcessing) {
        loginButton.setDisable(isProcessing);
        registerButton.setDisable(isProcessing);
        usernameField.setDisable(isProcessing);
    }

    @Override
    public void useEndStatus(Pair<Boolean, String> result) {
        if (result.getKey()) {
            UserData.getInstance().setUser(emailAddress);
            listener.useEndStatus(emailAddress);
            executorService.shutdownNow();
        } else {
            errorLabel.setText(result.getValue());
        }
        isProcessing = false;
        updateUIForProcessing(false);
    }

    public void loginAsUser(EndStatusListener<String> listener) {
        this.listener = listener;
    }
}

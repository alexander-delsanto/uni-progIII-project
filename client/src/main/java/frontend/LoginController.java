package frontend;

import backend.LoginService;
import backend.RegistrationService;
import backend.ServiceRequester;
import interfaces.EndStatusListener;
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
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final EmailValidator emailValidator = new EmailValidator();
    private EndStatusListener<String> listener = null;
    private boolean isProcessing = false;

    @FXML
    private void login() {
        initiateService(LoginService.class);
    }

    @FXML
    private void register() {
        initiateService(RegistrationService.class);
    }

    private <T extends ServiceRequester<Pair<Boolean, String>>> void initiateService(Class<T> serviceClass) {
        if (isProcessing || listener == null) {
            System.err.println("Listener is not set or process is already running.");
            return;
        }

        emailAddress = usernameField.getText();
        if (!emailValidator.isAddressValid(emailAddress)) {
            errorLabel.setText("Invalid email address");
            return;
        }

        isProcessing = true;
        updateUIForProcessing(true);

        try {
            T service = serviceClass.getDeclaredConstructor(String.class).newInstance(emailAddress);
            service.setEndStatusListener(this);
            executorService.execute(service);
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

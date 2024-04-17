package frontend;

import backend.UpdateService;
import frontend.util.StageWrapper;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.MailBox;
import model.UserData;
import model.message.EmailMessage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 400;
    private boolean firstEmails = true;
    int numNewEmails = 0;

    private StageWrapper stageWrapper;
    private final ScheduledExecutorService   executorService = Executors.newScheduledThreadPool(1);
    private Alert alert;
    private final MailBox mailBox = MailBox.getInstance();
    private final UserData userData = UserData.getInstance();

    @Override
    public void start(Stage stage) {
        this.stageWrapper = new StageWrapper(stage, "Login", WIDTH, HEIGHT);

        loadScenes();
        setParameters();
        stageWrapper.open();
    }

    public void loadScenes() {
        LoginController loginController = stageWrapper.setRootAndGetController(getClass().getResource("login-view.fxml"));
        if (loginController != null) {
            loginController.loginAsUser(this::loadMainScene);
        }
    }

    public void loadMainScene(String user) {
        stageWrapper.setRootAndGetController(getClass().getResource("main-view.fxml"));
        stageWrapper.setTitle("Email client");
        stageWrapper.setWidth(1600);
        stageWrapper.setHeight(900);
        stageWrapper.setMinWidth(1000);
        stageWrapper.setMinHeight(600);

        initializeAlert();
        UpdateService updateService = new UpdateService(userData.getUser());
        updateService.setEndStatusListener(this::addNewEmails);
        int UPDATE_INTERVAL = 5;
        executorService.scheduleAtFixedRate(updateService, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    private void addNewEmails(List<EmailMessage> emailMessages) {
        boolean online = emailMessages != null;
        mailBox.setOnline(online);
        if (!online || emailMessages.isEmpty()) return;

        mailBox.addEmails(userData.getUser(), emailMessages);
        if (firstEmails) {
            firstEmails = false;
            return;
        }

        numNewEmails += emailMessages.stream().filter(email ->
                !email.sender().equals(userData.getUser())).toList().size();

        if (numNewEmails > 0) {
            alert.setHeaderText("You have " + numNewEmails + " new email/s.");
            alert.show();
            numNewEmails = 0;
        }

    }

    private void initializeAlert() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New emails notification");
        alert.initOwner(stageWrapper.getStage());
    }

    private void setParameters() {
        stageWrapper.setIcon(getClass().getResource("assets/icon.png"));
        stageWrapper.setOnCloseRequest(e -> executorService.shutdownNow());

    }

    public static void main(String[] args) {
        launch();
    }
}

package frontend;

import backend.Updater;
import frontend.util.StageWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Email;
import model.MailBox;
import model.UserData;
import model.message.EmailMessage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private final int UPDATE_INTERVAL = 5;
    private static final int WIDTH = 550;
    private static final int HEIGHT = 400;

    private StageWrapper stageWrapper;
    private final ScheduledExecutorService   executorService = Executors.newScheduledThreadPool(1);

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
        UserData.getInstance().setUser(user);
        MailBox.getInstance().setUser(user);
        stageWrapper.setWidth(1600);
        stageWrapper.setHeight(900);

        Updater updater = new Updater();
        updater.setEndStatusListener(this::addNewEmails);
        executorService.scheduleAtFixedRate(updater, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    private void addNewEmails(List<EmailMessage> emailMessages) {
        System.out.println(emailMessages);
        MailBox.getInstance().addEmails(emailMessages);
    }

    private void setParameters() {
        stageWrapper.setIcon(getClass().getResource("assets/icon.png"));
        stageWrapper.setOnCloseRequest(e -> executorService.shutdownNow());
    }

    public static void main(String[] args) {
        launch();
    }
}

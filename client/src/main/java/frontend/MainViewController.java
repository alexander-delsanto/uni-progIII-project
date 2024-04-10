package frontend;

import backend.Email;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.StatusBar;

public class MainViewController {
    @FXML private ListView<Email> inboxListView;
    @FXML private ListView<Email> outboxListView;
    @FXML private StatusBarController statusBarController;
    private int i = 0;

    public void setStatusBar(StatusBar statusBar) {
        this.statusBarController.setStatusBar(statusBar);
    }

    @FXML
    public void handleButtonClick() {
        i++;
        statusBarController.setUser("clicked " + i + " times");
        statusBarController.setStatus("Modified " + i + " times");
    }
    @FXML
    public void changeTab() {

    }

    @FXML
    public void updateSelectedInbox() {

    }

    @FXML
    public void updateSelectedOutbox() {

    }

}

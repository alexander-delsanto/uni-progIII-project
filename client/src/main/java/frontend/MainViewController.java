package frontend;

import model.Email;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.UserData;

public class MainViewController {
    @FXML private ListView<Email> inboxListView;
    @FXML private ListView<Email> outboxListView;
    private final UserData userData = UserData.getInstance();
    private int i = 0;


    @FXML
    public void handleButtonClick() {
        userData.setName("clicked " + ++i + " times");
        userData.setStatus("Modified " + i + " times");
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

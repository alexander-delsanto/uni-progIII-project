package frontend;

import backend.ServiceRequester;
import model.message.EmailMessage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.UserData;

public class MainViewController {
    @FXML private ListView<EmailMessage> inboxListView;
    @FXML private ListView<EmailMessage> outboxListView;
    private final UserData userData = UserData.getInstance();
    private int i = 0;


    @FXML
    public void handleButtonClick() {
        /*ServiceRequester serviceRequester = new ServiceRequester();
        serviceRequester.run();*/
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

package frontend;

import backend.ServiceRequester;
import model.MailBox;
import model.message.EmailMessage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.UserData;

public class MainViewController {
    @FXML private ListView<EmailMessage> inboxListView;
    @FXML private ListView<EmailMessage> outboxListView;
    private final UserData userData = UserData.getInstance();

    @FXML
    private void initialize() {
        inboxListView.setItems(MailBox.getInstance().inboxObservableList());
        outboxListView.setItems(MailBox.getInstance().outboxObservableList());
    }

    @FXML
    public void handleButtonClick() {

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

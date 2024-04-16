package frontend;

import model.Email;
import model.MailBox;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class MainViewController {
    @FXML private ListView<Email> inboxListView;
    @FXML private ListView<Email> outboxListView;
    private final MailBox mailBox = MailBox.getInstance();

    @FXML
    private void initialize() {
        inboxListView.setItems(mailBox.inboxObservableList().sorted(Comparator.comparing(Email::getTimestamp).reversed()));
        outboxListView.setItems(mailBox.outboxObservableList().sorted(Comparator.comparing(Email::getTimestamp).reversed()));
    }

    @FXML
    public void changeTab() {
        if (inboxListView == null || outboxListView == null) return;

        inboxListView.getSelectionModel().clearSelection();
        outboxListView.getSelectionModel().clearSelection();
        mailBox.setSelectedEmail(null);
    }

    @FXML
    public void updateSelectedInbox() { mailBox.setSelectedEmail(inboxListView.getSelectionModel().getSelectedItem()); }

    @FXML
    public void updateSelectedOutbox() { mailBox.setSelectedEmail(outboxListView.getSelectionModel().getSelectedItem()); }

}

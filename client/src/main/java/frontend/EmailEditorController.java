package frontend;

import interfaces.EndStatusListener;
import interfaces.EndStatusNotifier;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Email;

public class EmailEditorController implements EndStatusNotifier<Email> {
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField subjectField;
    @FXML private TextField bodyField;

    private Email email;
    private EndStatusListener<Email> endStatusListener;

    public void setMail(Email email) {
        this.email = email;
        fromField.textProperty().bind(email.senderProperty());
        toField.textProperty().bindBidirectional(email.recipientsProperty());
        subjectField.textProperty().bindBidirectional(email.subjectProperty());
        bodyField.textProperty().bindBidirectional(email.bodyProperty());
    }
    @FXML
    private void send() { endStatusListener.useEndStatus(email); }

    @FXML
    private void cancel() {
        // Close window
    }

    @Override
    public void setEndStatusListener(EndStatusListener<Email> listener) {
        this.endStatusListener = listener;
    }
}

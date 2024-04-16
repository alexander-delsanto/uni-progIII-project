package frontend.component;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.MailBox;

public class EmailViewController {
    private final MailBox mailBox = MailBox.getInstance();
    @FXML TextField fromField;
    @FXML TextField toField;
    @FXML TextField subjectField;
    @FXML TextField dateField;
    @FXML TextArea contentField;
    @FXML VBox emailView;

    @FXML
    private void initialize() {

        fromField.textProperty().bind(mailBox.getSelectedEmail().senderProperty());
        toField.textProperty().bind(mailBox.getSelectedEmail().recipientsProperty());
        subjectField.textProperty().bind(mailBox.getSelectedEmail().subjectProperty());
        dateField.textProperty().bind(mailBox.getSelectedEmail().timestampProperty());
        contentField.textProperty().bind(mailBox.getSelectedEmail().bodyProperty());
    }

}

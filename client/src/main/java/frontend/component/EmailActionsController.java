package frontend.component;

import backend.DeleteService;
import backend.SendService;
import backend.ServiceRequester;
import frontend.EmailEditorController;
import frontend.MainViewController;
import frontend.util.StageWrapper;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import javafx.scene.Node;
import model.Email;
import model.MailBox;
import model.UserData;

import java.util.Optional;

public class EmailActionsController {
    @FXML Button deleteButton;
    @FXML Button replyButton;
    @FXML Button replyAllButton;
    @FXML Button forwardButton;
    UserData userData = UserData.getInstance();
    MailBox mailBox = MailBox.getInstance();

    @FXML
    private void initialize() {
        BooleanBinding emptySelection = mailBox.selectionExistsProperty().not();
        forwardButton.disableProperty().bind(emptySelection);
        deleteButton.disableProperty().bind(emptySelection.or(mailBox.onlineProperty().not()));

        BooleanBinding invalidSelection = mailBox.getSelectedEmail().senderProperty().isEqualTo(userData.getUser());
        replyButton.disableProperty().bind(emptySelection.or(invalidSelection));
        replyAllButton.disableProperty().bind(emptySelection.or(invalidSelection));
    }

    @FXML
    private void openEmailEditor(ActionEvent event) {
        Window owner = ((Node)event.getSource()).getScene().getWindow();
        String buttonId = ((Button)event.getSource()).getId();
        Email mailSelected  = mailBox.getSelectedEmail();

        Email formattedEmail = switch (buttonId) {
            case "replyButton" -> new Email(userData.getUser(), mailSelected.getSender(),
                    "Re: " + mailSelected.getSubject(), mailSelected.formatForReply());
            case "replyAllButton" -> new Email(userData.getUser(), mailSelected.getReplyAllRecipients(userData.getUser()),
                    "Re: " + mailSelected.getSubject(), mailSelected.formatForReply());
            case "forwardButton" -> new Email(userData.getUser(), null,
                    "Fwd: " + mailSelected.getSubject(), mailSelected.formatForReply());
            case null, default -> new Email(userData.getUser(), null, null, null);
        };

        openWindow(owner, formattedEmail);
    }

    private void openWindow(Window owner, Email formattedEmail) {
        StageWrapper stageWrapper = new StageWrapper(null, "Mail Editor", 800, 600);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainViewController.class.getResource("assets/icon.png"));

        EmailEditorController emailEditorController = stageWrapper.setRootAndGetController(MainViewController.class.getResource("email-editor-view.fxml"));
        emailEditorController.setMail(formattedEmail);
        emailEditorController.setEndStatusListener(email -> handleEmail(email, stageWrapper, emailEditorController));

        stageWrapper.open();
    }

    public void handleEmail(Email email, StageWrapper stage, EmailEditorController emailEditorController) {
        if (email == null) {
            stage.close();
            return;
        }

        ServiceRequester<String> serviceRequester;
        serviceRequester = new SendService(userData.getUser(), email);
        serviceRequester.setEndStatusListener(errorString -> {
            if (errorString != null) {
                emailEditorController.setErrorLabel(errorString);
                return;
            }
            stage.close();
        });
        serviceRequester.run();
    }

    @FXML
    private void deleteEmail(ActionEvent event) {
        Alert alert = getDeleteAlert(event);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Email toDelete = new Email(mailBox.getSelectedEmail());
            DeleteService deleteService = new DeleteService(userData.getUser(), toDelete);
            deleteService.setEndStatusListener(errorString -> {
                if (errorString != null) {
                    System.err.println("Error deleting email: " + errorString);
                    return;
                }
                mailBox.deleteSelectedEmail(userData.getUser(), toDelete);
            });
            new Thread(deleteService).start();
        }
    }

        private Alert getDeleteAlert(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Email");
            alert.setHeaderText("Are you sure you want to delete this email?");
            alert.initOwner(((Node)event.getSource()).getScene().getWindow());
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            return alert;
        }
}

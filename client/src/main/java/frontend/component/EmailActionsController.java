package frontend.component;

import backend.DeleteService;
import backend.SendService;
import backend.ServiceRequester;
import frontend.EmailEditorController;
import frontend.MainViewController;
import frontend.util.StageWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import javafx.scene.Node;
import model.Email;
import model.MailBox;
import model.UserData;

import java.util.Optional;

public class EmailActionsController {
    UserData userData = UserData.getInstance();
    MailBox mailBox = MailBox.getInstance();


    @FXML
    private void openEmailEditor(ActionEvent event) {
        Window owner = ((Node)event.getSource()).getScene().getWindow();

        openWindow(owner);
    }

    private void openWindow(Window owner) {
        StageWrapper stageWrapper = new StageWrapper(null, "Mail Editor", 650, 450);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainViewController.class.getResource("assets/icon.png"));

        EmailEditorController emailEditorController = stageWrapper.setRootAndGetController(MainViewController.class.getResource("email-editor-view.fxml"));
        emailEditorController.setMail(new Email(userData.getUser(), null, null, null));
        emailEditorController.setEndStatusListener(email -> handleEmail(email, stageWrapper, emailEditorController));

        stageWrapper.open();
    }

    public void handleEmail(Email email, StageWrapper stage, EmailEditorController emailEditorController) {
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
    private void deleteSelectedEmail(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Email");
        alert.setHeaderText("Are you sure you want to delete this email?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

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

    private void setModalAndIcon(ActionEvent event, StageWrapper stageWrapper) {
        Window owner = ((Node)event.getSource()).getScene().getWindow();
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainViewController.class.getResource("assets/icon.png"));
    }
}

package frontend.component;

import backend.ServiceRequester;
import frontend.EmailEditorController;
import frontend.MainViewController;
import frontend.util.StageWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.scene.Node;
import model.Email;
import model.MailBox;

public class EmailActionsController {

    @FXML
    private void openEmailEditor(ActionEvent event) {
        Window owner = ((Node)event.getSource()).getScene().getWindow();

        openWindow(owner);
    }

    private void openWindow(Window owner) {
        StageWrapper stageWrapper = new StageWrapper(null, "Mail Editor", 650, 450);
        stageWrapper.setModal(owner);
        stageWrapper.setIcon(MainViewController.class.getResource("images/icon.png"));

        EmailEditorController emailEditorController = stageWrapper.setRootAndGetController(MainViewController.class.getResource("email-editor-view.fxml"));
        emailEditorController.setMail(new Email(MailBox.getInstance().getUser(), null, null, null));
        emailEditorController.setEndStatusListener(email -> handleEmail(email, stageWrapper, emailEditorController));

        stageWrapper.open();
    }

    public void handleEmail(Email email, StageWrapper stage, EmailEditorController emailEditorController) {
        ServiceRequester serviceRequester = new ServiceRequester(MailBox.getInstance().getUser(), email);
        serviceRequester.run();
        stage.close();

    }
}

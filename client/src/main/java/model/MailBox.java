package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.message.EmailMessage;

import java.util.List;

public class MailBox {
    private final Email selectedEmail = new Email("", "", "", "");
    private final SimpleBooleanProperty selectionExists = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty online = new SimpleBooleanProperty(false);

    private final ObservableList<Email> inbox = FXCollections.observableArrayList();
    private final ObservableList<Email> outbox = FXCollections.observableArrayList();

    private static MailBox instance = null;
    public static synchronized MailBox getInstance(){
        if(instance == null){
            instance = new MailBox();
        }
        return instance;
    }
    private MailBox() { }

    public ObservableList<Email> inboxObservableList() { return inbox; }
    public ObservableList<Email> outboxObservableList() { return outbox; }

    public void addEmails(String user, List<EmailMessage> emailMessages) {
        if (emailMessages == null || emailMessages.isEmpty()) return;
        for (EmailMessage emailMessage : emailMessages) {
            addEmail(user, emailMessage);
        }
    }

    public void addEmail(String user, EmailMessage emailMessage) {
        if (emailMessage.sender().equals(user)) {
            outbox.add(new Email(emailMessage));
        } else {
            inbox.add(new Email(emailMessage));
        }
    }

    public void deleteSelectedEmail(String user, Email email) {
        if (email == null) return;
        System.out.println(email.equals(selectedEmail));
        if (email.equals(selectedEmail)) setSelectedEmail(null);
        if (user.equals(email.getSender())) {
            outbox.remove(email);
        } else {
            inbox.remove(email);
        }
    }

    public Email getSelectedEmail() {
        return selectedEmail;
    }

    @FXML
    public void setSelectedEmail(Email email) {
        if (email == null) {
            email = new Email("", "", "", "");
            selectionExists.set(false);
        } else {
            selectionExists.set(true);
        }
        selectedEmail.senderProperty().bind(email.senderProperty());
        selectedEmail.recipientsProperty().bind(email.recipientsProperty());
        selectedEmail.subjectProperty().bind(email.subjectProperty());
        selectedEmail.bodyProperty().bind(email.bodyProperty());
        selectedEmail.timestampProperty().bind(email.timestampProperty());
        selectedEmail.setId(email.getId());
    }


    public SimpleBooleanProperty selectionExistsProperty() { return selectionExists; }

    public SimpleBooleanProperty onlineProperty() { return online; }
    public void setOnline(boolean online) { this.online.set(online); }
}

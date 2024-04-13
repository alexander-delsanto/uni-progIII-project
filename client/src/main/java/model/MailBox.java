package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.message.EmailMessage;

import java.util.List;

public class MailBox {
    private String user;

    private static MailBox instance = null;
    public static synchronized MailBox getInstance(){
        if(instance == null){
            instance = new MailBox();
        }
        return instance;
    }
    private MailBox(){}
    
    private final ObservableList<EmailMessage> inbox = FXCollections.observableArrayList();
    private final ObservableList<EmailMessage> outbox = FXCollections.observableArrayList();

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public void addEmails(List<EmailMessage> emailMessages) {
        if (emailMessages == null || emailMessages.isEmpty()) return;
        for (EmailMessage emailMessage : emailMessages) {
            addEmail(emailMessage);
        }
    }

    public void addEmail(EmailMessage emailMessage) {
        if (emailMessage.sender().equals(user)) {
            outbox.add(emailMessage);
        } else {
            inbox.add(emailMessage);
        }
    }

    public void deleteEmail(EmailMessage emailMessage) {
        if (emailMessage.sender().equals(user)) {
            outbox.remove(emailMessage);
        } else {
            inbox.remove(emailMessage);
        }
    }


}

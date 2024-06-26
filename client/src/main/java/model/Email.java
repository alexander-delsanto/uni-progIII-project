package model;

import javafx.beans.property.SimpleStringProperty;
import model.message.EmailMessage;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Email implements Serializable {
    private final SimpleStringProperty sender = new SimpleStringProperty("");
    private final SimpleStringProperty recipients = new SimpleStringProperty("");
    private final SimpleStringProperty subject = new SimpleStringProperty("");
    private final SimpleStringProperty body = new SimpleStringProperty("");
    private final SimpleStringProperty timestamp = new SimpleStringProperty("");
    private int id;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss");

    public Email(String sender, String recipients, String subject, String body) {
        this.sender.set(sender == null ? "" : sender);
        this.recipients.set(recipients == null ? "" : recipients);
        this.subject.set(subject == null ? "" : subject);
        this.body.set(body == null ? "" : body);
    }

    public Email(EmailMessage emailMessage) {
        this(emailMessage.sender(), emailMessage.recipients(), emailMessage.subject(), emailMessage.body());
        this.id = emailMessage.id();
        if (emailMessage.sentTime() != null)
            this.timestamp.set(dateFormat.format(emailMessage.sentTime()));
    }

    public Email(Email email) {
        this.sender.set(email.sender.get());
        this.recipients.set(email.recipients.get());
        this.subject.set(email.subject.get());
        this.body.set(email.body.get());
        this.timestamp.set(email.timestamp.get());
        this.id = email.id;
    }

    public EmailMessage toEmailMessage() {
        Date date = null;
        try {
            date = dateFormat.parse(getTimestamp());
        } catch (ParseException e) { /* do noting */ }
        return new EmailMessage(getSender(), getRecipients(), getSubject(), getBody(), getId(), date);
    }

    public String formatForReply() {
        StringBuilder newBody = new StringBuilder("\n");
        newBody.append("On ").append(getTimestamp()).append(", ").append(getSender()).append(" wrote:\n");
        for (String line : getBody().split("\n")) {
            newBody.append("> ").append(line).append("\n");
        }
        return newBody.toString();
    }

    public String getReplyAllRecipients(String sender) {
        String[] recipients = getRecipients().split(",");
        StringBuilder replyAllRecipients = new StringBuilder();
        replyAllRecipients.append(getSender()).append(", ");
        for (int i = 0; i < recipients.length; i++) {
            String recipient = recipients[i].trim();
            if (!recipient.equals(sender)) {
                replyAllRecipients.append(recipient);
                if (i < recipients.length - 2) {
                    replyAllRecipients.append(", ");
                }
            }
        }
        return replyAllRecipients.toString();
    }

    @Override
    public String toString() {
        return subject.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Email email = (Email) obj;
        return this.getSender().equals(email.getSender()) &&
                this.getRecipients().equals(email.getRecipients()) &&
                this.getSubject().equals(email.getSubject()) &&
                this.getBody().equals(email.getBody()) &&
                this.getTimestamp().equals(email.getTimestamp());
    }

    public SimpleStringProperty senderProperty() { return sender; }
    public SimpleStringProperty recipientsProperty() { return recipients; }
    public SimpleStringProperty subjectProperty() { return subject; }
    public SimpleStringProperty bodyProperty() { return body; }
    public SimpleStringProperty timestampProperty() { return timestamp; }

    public String getSender() { return sender.get(); }
    public String getRecipients() { return recipients.get(); }
    public String getSubject() { return subject.get(); }
    public String getBody() { return body.get(); }
    public String getTimestamp() { return timestamp.get(); }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}

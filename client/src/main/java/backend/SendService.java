package backend;

import model.Email;
import model.EmailValidator;
import model.message.EmailMessage;
import model.message.Message;
import java.util.Collections;
import java.util.List;

public class SendService extends ServiceRequester<String> {
    private final String user;
    private final Email email;
    private final EmailValidator emailValidator = new EmailValidator();

    public SendService(String user, Email message) {
        this.user = user;
        this.email = message;
    }

    @Override
    public String callService() {
        if (email == null || !emailValidator.checkValidity(email.getRecipients())) {
            return "Improper email address.";
        }
        List<EmailMessage> messages = Collections.singletonList(email.toEmailMessage());
        Message message = new Message(user, Message.OP_SEND, "", messages);
        Message response = sendMessage(message);

        if (response == null) {
            return "Couldn't reach server.";
        }
        return response.errorMessage();
    }
}

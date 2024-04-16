package backend;

import model.Email;
import model.message.EmailMessage;
import model.message.Message;

import java.util.Collections;
import java.util.List;

public class DeleteService extends ServiceRequester<String> {
    Email toDelete;
    private final String user;

    public DeleteService(String user, Email toDelete) {
        this.user = user;
        this.toDelete = toDelete;
    }

    @Override
    public String callService() {
        List<EmailMessage> emailMessages = Collections.singletonList(toDelete.toEmailMessage());
        Message message = new Message(user, Message.OP_DELETE, null, emailMessages);
        Message response = sendMessage(message);
        if (response == null)
            return "Cannot communicate with server.";
        return null;
    }

}

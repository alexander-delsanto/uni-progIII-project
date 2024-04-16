package backend;

import model.MailBox;
import model.message.EmailMessage;
import model.message.Message;

import java.util.List;

public class Updater extends ServiceRequester<List<EmailMessage>> {
    private int lastRequest = Message.OP_GET_ALL;

    @Override
    protected List<EmailMessage> callService() {
        Message message = new Message(MailBox.getInstance().getUser(), lastRequest, null, null);
        Message response = sendMessage(message);

        if (response == null) return null;

        lastRequest = response.operation();
        return response.emailMessages();
    }
}

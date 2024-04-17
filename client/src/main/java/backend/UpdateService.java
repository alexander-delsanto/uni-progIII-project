package backend;

import model.message.EmailMessage;
import model.message.Message;

import java.util.List;

public class UpdateService extends ServiceRequester<List<EmailMessage>> {
    private int lastRequest = Message.OP_GET_ALL;
    private final String user;

    public UpdateService(String user) {
        this.user = user;
    }

    @Override
    protected List<EmailMessage> callService() {
        Message message = new Message(user, lastRequest, null, null);
        Message response = sendMessage(message);

        if (response == null) return null;

        lastRequest = response.operation();
        return response.emailMessages();
    }
}

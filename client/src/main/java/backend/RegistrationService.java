package backend;

import interfaces.EndStatusListener;
import javafx.util.Pair;
import model.message.Message;

public class RegistrationService extends ServiceRequester<Pair<Boolean, String>> {
    private final String user;

    public RegistrationService(String user) {
        this.user = user;
    }

    @Override
    public Pair<Boolean, String> callService() {
        Message message = new Message(user, Message.OP_REGISTER, "",null);
        Message response = sendMessage(message);
        Pair<Boolean, String> res;
        if (response.errorMessage() == null)
            res = new Pair<>(true, null);
        else
            res = new Pair<>(false, "User already exists.");
        return res;
    }
}

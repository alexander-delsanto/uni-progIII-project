package backend;

import interfaces.EndStatusListener;
import javafx.util.Pair;
import model.message.Message;

public class LoginService extends ServiceRequester<Pair<Boolean, String>> {
    private final String user;

    public LoginService(String user) {
        this.user = user;
    }

    @Override
    public Pair<Boolean, String> callService() {
        Message message = new Message(user, Message.OP_LOGIN, "",null);
        Message response = sendMessage(message);
        Pair<Boolean, String> res;
        if (response == null)
            res = new Pair<>(false, "Cannot communicate with server.");
        else if (response.errorMessage() == null)
            res = new Pair<>(true, null);
        else
            res = new Pair<>(false, "User does not exist.");
        return res;
    }
}

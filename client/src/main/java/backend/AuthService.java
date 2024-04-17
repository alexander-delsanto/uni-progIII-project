package backend;

import javafx.util.Pair;
import model.message.Message;

public class AuthService extends ServiceRequester<Pair<Boolean, String>> {
    private final String user;
    private final boolean isLogin;

    public AuthService(String user, boolean isLogin) {
        this.user = user;
        this.isLogin = isLogin;
    }

    @Override
    public Pair<Boolean, String> callService() {
        Message message;
        if (isLogin) {
            message = new Message(user, Message.OP_LOGIN, "",null);
        } else {
            message = new Message(user, Message.OP_REGISTER, "",null);
        }
        Message response = sendMessage(message);
        Pair<Boolean, String> res;
        if (response == null)
            res = new Pair<>(false, "Couldn't reach server.");
        else if (response.errorMessage() == null)
            res = new Pair<>(true, null);
        else if (isLogin)
            res = new Pair<>(false, "User does not exist.");
        else
            res = new Pair<>(false, "User already exists.");
        return res;
    }
}

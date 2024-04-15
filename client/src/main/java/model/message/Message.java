package model.message;

import java.util.List;

public record Message(String user, int operation, String errorMessage, List<EmailMessage> emailMessages) {
    public static final int OP_REGISTER = -2;
    public static final int OP_LOGIN = -1;
    public static final int OP_GET_ALL = 0;
    public static final int OP_SEND = 1;
    public static final int OP_DELETE = 2;
}

package model;

import java.util.List;

public record Message(String user, int operation, String errorMessage, List<EmailMessage> emailMessages) {
    public static final int OP_REGISTER = -4;
    public static final int OP_LOGIN = -3;
    public static final int OP_SEND = -2;
    public static final int OP_DELETE = -1;
    public static final int OP_GET_ALL = 0;
}

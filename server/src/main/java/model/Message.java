package model;

import java.util.List;

public record Message(String user, int operation, List<Email> emails) {
    public static final int OP_GET_ALL = 0;
    public static final int OP_SEND = 1;
    public static final int OP_DELETE = 2;
}

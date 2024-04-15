package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.Message;

import java.io.*;
import java.net.Socket;

public class ConnectionReplier implements Runnable {
    private final Logger logger;
    private final Socket socket;
    private Gson gson = new Gson();

    public ConnectionReplier(Logger logger, Socket socket) {
        this.logger = logger;
        this.socket = socket;
    }

    @Override
    public void run() {
        logger.log("Connection established with " + socket.getInetAddress());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String receivedMessage = reader.readLine();
                Message message = gson.fromJson(receivedMessage, Message.class);
                System.out.println(message);
                Message reply = replyToMessage(message);
                System.out.println(reply);
                writer.println(gson.toJson(reply));
            }
        } catch (IOException e) {
            logger.log("Error during communication with client.");
        }
        try {
            socket.close();
        } catch (IOException e) {
            logger.log("Could not close socket.");
        }

    }

    private Message replyToMessage(Message message) {
        Message response;
        System.out.println("in replyToMessage");
        switch (message.operation()) {
            case Message.OP_REGISTER:
                boolean res = false;
                try {
                    res = AuthenticatorService.register(message.user());
                } catch (Error e) {
                    logger.log(e.getMessage());
                }
                if (res) {
                    response = new Message(message.user(), message.operation(), null, null);
                    logger.log("Successful registration as " + message.user());
                } else {
                    response = new Message(message.user(), message.operation(), "Error", null);
                    logger.log("Failed registration as " + message.user());
                }
                return response;
            case Message.OP_LOGIN:
                System.out.println("LOGIN");
                if (AuthenticatorService.login(message.user())) {
                    response = new Message(message.user(), message.operation(), null, null);
                    logger.log("Successful login as " + message.user());
                } else {
                    response = new Message(message.user(), message.operation(), "Error", null);
                    logger.log("Failed login as " + message.user());
                }
                return response;
            default:
                return null;
        }
    }
}

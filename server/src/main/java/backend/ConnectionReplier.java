package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import model.EmailMessage;
import model.Message;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionReplier implements Runnable {
    private final Logger logger;
    private final Socket socket;
    private final Gson gson = new Gson();

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
                Message reply = replyToMessage(message);
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
        return switch (message.operation()) {
            case Message.OP_REGISTER -> handleRegistration(message);
            case Message.OP_LOGIN -> handleLogin(message);
            case Message.OP_SEND -> handleSendEmail(message);
            case Message.OP_DELETE -> handleDeleteEmail(message);
            default -> handleGetEmails(message);
        };
    }

    private Message handleRegistration(Message message) {
        boolean result;
        try {
            result = AuthenticatorService.register(message.user());
        } catch (Exception e) {
            logger.log("Registration failed for " + message.user() + ": " + e.getMessage());
            return new Message(message.user(), message.operation(), "Error", null);
        }
        if (result) {
            logger.log("Successful registration for " + message.user());
            return new Message(message.user(), message.operation(), null, null);
        } else {
            logger.log("Failed registration for " + message.user());
            return new Message(message.user(), message.operation(), "Error", null);
        }
    }

    private Message handleLogin(Message message) {
        boolean loggedIn;
        try {
            loggedIn = AuthenticatorService.login(message.user());
        } catch (Exception e) {
            logger.log("Login failed for " + message.user() + ": " + e.getMessage());
            return new Message(message.user(), message.operation(), "Error", null);
        }
        if (loggedIn) {
            logger.log("Successful login for " + message.user());
            return new Message(message.user(), message.operation(), null, null);
        } else {
            logger.log("Failed login for " + message.user());
            return new Message(message.user(), message.operation(), "Error", null);
        }
    }

    private Message handleGetEmails(Message message) {
        FileManager userFile = FileManager.get(message.user());
        List<EmailMessage> emailMessages = userFile.getEmails();

        int id = userFile.getNextId(emailMessages);
        final List<EmailMessage> result = emailMessages.stream().filter((mail) -> mail.id() >= message.operation()).toList();
        return new Message(message.user(), id, null, result);
    }

    private Message handleSendEmail(Message message) {
        if (message.emailMessages() == null || message.emailMessages().isEmpty()) {
            logger.log("Send email failed: No emails provided.");
            throw new IllegalArgumentException("No emails provided in the message.");
        }
        EmailMessage toSend = message.emailMessages().getFirst();
        if (toSend.recipients() == null || toSend.sender() == null) {
            logger.log("Send email failed: Sender or recipients are null.");
            throw new IllegalArgumentException("Sender or recipients are null.");
        }

        FileManager senderFile = FileManager.get(message.user());
        if (senderFile == null) {
            logger.log("Failed to access file manager for sender: " + message.user());
            return new Message(message.user(), message.operation(), "Error accessing sender's file", null);
        }
        List<FileManager> recipientsFiles = new ArrayList<>();
        for (String recipient : toSend.recipients().split(",")) {
            try {
                FileManager recipientFile = FileManager.get(recipient.trim());
                if (recipientFile != null) {
                    recipientsFiles.add(recipientFile);
                } else {
                    logger.log("Failed to access file manager for recipient: " + recipient);
                }
            } catch (IllegalArgumentException e) {
                logger.log("Failed to send emails from " + message.user() + ": one or more recipients don't exist.");
                return new Message(message.user(), message.operation(), e.getMessage(), null);
            }
        }

        senderFile.addEmails(Collections.singletonList(toSend));
        for (FileManager recipientFile : recipientsFiles) {
            recipientFile.addEmails(Collections.singletonList(toSend));
        }
        logger.log("Email sent successfully from " + message.user());
        return new Message(message.user(), Message.OP_SEND, null, null);
    }

    public Message handleDeleteEmail(Message message) {
        logger.log("Deleting " + message.emailMessages().size() +  " emails for " + message.user());
        FileManager userFile = FileManager.get(message.user());
        System.out.println("Got file manager for " + message.user() + "\n" + message.emailMessages());
        userFile.deleteEmails(message.emailMessages());
        return new Message(message.user(), message.operation(), null, null);
    }
}

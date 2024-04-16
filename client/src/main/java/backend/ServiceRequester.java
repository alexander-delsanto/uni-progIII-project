package backend;

import com.google.gson.Gson;
import interfaces.EndStatusListener;
import interfaces.EndStatusNotifier;
import javafx.application.Platform;
import model.message.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class ServiceRequester<T> implements Runnable, EndStatusNotifier<T> {
    private EndStatusListener<T> listener;
    private final Gson gson = new Gson();

    @Override
    public void setEndStatusListener(EndStatusListener<T> listener) {
        this.listener = listener;
    }

    protected abstract T callService();

    @Override
    public void run() {
        T result = callService();
        if (listener != null)
            Platform.runLater(() -> listener.useEndStatus(result));
    }

    public Message sendMessage(Message message) {
        try (Socket socket = new Socket("localhost", 60000)) {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                try (Scanner in = new Scanner(socket.getInputStream())) {
                    out.println(gson.toJson(message, Message.class));
                    return gson.fromJson(in.nextLine(), Message.class);
                }
            } catch (IOException e) {
                System.err.println("Error during communication with server: " + e.getMessage());
            }
        } catch (IOException ignored) {

        } catch (Throwable t) {
            System.err.println("Error with data manipulation: " + t.getMessage());
        }
        return null;
    }
}

package backend;

import com.google.gson.Gson;
import interfaces.Logger;
import javafx.application.Platform;
import model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable, Logger {
    private Logger logger;
    //private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static Server serverInstance;
    public static Server getServer(Logger logger) {
        if (serverInstance == null) {
            serverInstance = new Server();
            serverInstance.logger = logger;
        }

        return serverInstance;
    }

    private Server() {}

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(60000)) {
            serverSocket.setSoTimeout(2000);
            Socket socket;

            logger.log("Server initialized. Listening on port 60000");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    socket = serverSocket.accept();
                    log("Connection established with " + socket.getInetAddress());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String json = reader.readLine();
                    processGson(json);
                    socket.close();
                } catch (InterruptedIOException e) {
                    continue;
                }

            }
        } catch (IOException e) {
            log("Fatal error.");
        }
        //executorService.shutdown();
    }

    @Override
    public void log(String logMessage) {
        Platform.runLater(() -> logger.log(logMessage));
    }

    private void processGson(String json) {
        Gson gson = new Gson();
        Message message = gson.fromJson(json, Message.class);
        log("Received message: " + message);
    }
}

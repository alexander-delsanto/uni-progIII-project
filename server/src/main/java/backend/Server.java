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
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
            Socket socket;

            log("Server listening on port 60000");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    socket = serverSocket.accept();
                } catch (InterruptedIOException e) {
                    continue;
                }
                ConnectionReplier connectionReplier = new ConnectionReplier(this, socket);
                executorService.execute(connectionReplier);
            }
        } catch (IOException e) {
            log("Fatal error.");
        }
        executorService.shutdown();
    }

    @Override
    public void log(String logMessage) {
        Platform.runLater(() -> logger.log(logMessage));
    }
}

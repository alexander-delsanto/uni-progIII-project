package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        final int port = 12345; // Port number the server will listen on
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Email Server is listening on port " + port);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");

                    EmailHandler handler = new EmailHandler(socket);
                    new Thread(handler).start();

                } catch (IOException e) {
                    System.out.println("Server exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            e.printStackTrace();
        }
    }
}
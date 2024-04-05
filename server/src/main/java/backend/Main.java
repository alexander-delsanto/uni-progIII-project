package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;


public class Main {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started.");
            try(Socket socket = serverSocket.accept()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package client;

import com.google.gson.Gson;
import model.Email; // Assuming Email record is in the package backend and accessible from here

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        Email email = new Email("sender@example.com", Arrays.asList("recipient1@example.com", "recipient2@example.com"), "Test Subject", "This is a test body", new Date());

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Connected to the email server");

            Gson gson = new Gson();
            String emailJson = gson.toJson(email);

            try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true)) {
                out.println(emailJson);
                System.out.println("Email sent: " + emailJson);
            }

        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

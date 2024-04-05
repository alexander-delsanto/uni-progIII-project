package backend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        String sender = "test@test.com";
        ArrayList<String> recipient = new ArrayList<>();
        recipient.add("user@test.com");
        String subject = "Test email";
        String body = "Hello, World!";
        Mail mail = new Mail(sender, recipient, subject, body, new Date());

        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        String json = gson.toJson(mail);
        System.out.println(json);

/*        try {
            ObjectOutputStream out;
            ObjectInputStream in;
            try (Socket socket = new Socket("localhost", 12345)) {
                System.out.println("Connection established.");

                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            }

            String sender = "test@test.com";
            ArrayList<String> recipient = new ArrayList<>();
            recipient.add("user@test.com");
            String body = "Hello, World!";
            Mail mail = new Mail(sender, recipient, body, null, null);
            out.writeObject(mail);
            System.out.println(in.readObject());


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Client stopped."); */
    }
}

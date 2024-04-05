package backend;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class EmailHandler implements Runnable {

    private final Socket socket;

    public EmailHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Gson gson = new Gson();
            Email email = gson.fromJson(reader.readLine(), Email.class);
            System.out.println("Received email from " + email.sender());
            processEmail(email);

        } catch (Exception e) {
            System.out.println("Handler exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void processEmail(Email email) {
        for (String recipient : email.recipients()) {
            File userFile = new File(recipient + ".json");

            try {
                if (!userFile.exists()) {
                    userFile.createNewFile();
                }

                appendEmailToFile(userFile, email);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendEmailToFile(File userFile, Email email) throws IOException {
        Gson gson = new Gson();
        try (FileWriter fw = new FileWriter(userFile, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw); {
                String emailJson = gson.toJson(email);
                out.println(emailJson);
            }
        }
    }
}

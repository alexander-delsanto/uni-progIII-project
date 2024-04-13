package backend;

import com.google.gson.Gson;
import model.Email;
import model.message.EmailMessage;
import model.message.Message;

import java.util.ArrayList;
import java.util.Date;

public class ServiceRequester implements Runnable {
    private final Gson gson = new Gson();
    String user;
    Email email;

    public ServiceRequester(String user, Email email) {
        this.user = user;
        this.email = email;
    }

    @Override
    public void run() {

        System.out.println("ServiceRequester is running");
        System.out.println(gson.toJson(email.toEmailMessage()) + " is the message");
        /*try (Socket socket = new Socket("localhost", 60000)) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                try (Scanner scanner = new Scanner(socket.getInputStream())) {
                    writer.println(gson.toJson(new Message("user", 0, null), Message.class));
                    System.out.println(gson.fromJson(scanner.nextLine(), Message.class));
                }
            } catch (IOException exc) {
                System.err.println("ERROR: during communication with server: " + exc.getMessage());
            }
        } catch (IOException e) {
            System.err.println("ERROR: while manipulating data: " + e.getMessage());
        } catch (Throwable throwable) {
            System.err.println("ERROR: while manipulating data: " + throwable.getMessage());
        }*/
    }

/*        public Message sendMessage(Message message) {
            try (Socket socket = new Socket("localhost", 8080)) {
                try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                    try (Scanner scanner = new Scanner(socket.getInputStream())) {
                        writer.println(gson.toJson(message, Message.class));
                        return gson.fromJson(scanner.nextLine(), Message.class);
                    }
                }catch (IOException exc) {
                    System.err.println("ERROR: during communication with server: " + exc.getMessage());
                }
            } catch (IOException e) {

            } catch (Throwable throwable){
                System.err.println("ERROR: while manipulating data: " + throwable.getMessage());
            }

            return null;
        }*/


}

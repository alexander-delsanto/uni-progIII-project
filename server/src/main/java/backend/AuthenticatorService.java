package backend;

import java.io.File;
import java.io.IOException;

public class AuthenticatorService {

    public static boolean login(String emailAddress) {
        return getFile(emailAddress).exists();
    }

    public static synchronized boolean register(String emailAddress) {
        File file = getFile(emailAddress);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                throw new Error("Couldn't create file.");
            }
        }
        return false;
    }

    private static File getFile(String emailAddress) {
        return new File("emails/" + emailAddress + ".json");
    }
}

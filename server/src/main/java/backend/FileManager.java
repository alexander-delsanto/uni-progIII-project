package backend;

import com.google.gson.Gson;
import model.EmailMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileManager {
    private static final ConcurrentHashMap<String, FileManager> openedFileManager = new ConcurrentHashMap<>();
    private static final String EMAIL_DIRECTORY = "emails/";
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final File file;
    private final Gson gson = new Gson();

    private FileManager(File file) {
        this.file = file;
    }

    public static FileManager get(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null.");
        }
        String filePath = EMAIL_DIRECTORY + filename + ".json";
        File file = new File(filePath);
        if (!file.exists())
            throw new IllegalArgumentException("One or more users do not exist.");
        return openedFileManager.computeIfAbsent(filePath, f -> new FileManager(file));
    }

    public List<EmailMessage> getEmails() { return loadEmails(); }

    public int getNextId(List<EmailMessage> emailMessages) {
        if (emailMessages == null || emailMessages.isEmpty())
            return 0;
        return emailMessages.getLast().id() + 1;
    }

    private List<EmailMessage> loadEmails() {
        rwLock.readLock().lock();
        try {
            if (!file.exists()) {
                return new ArrayList<>();
            }
            try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                EmailMessage[] emails = gson.fromJson(reader, EmailMessage[].class);
                return emails != null ? new ArrayList<>(Arrays.asList(emails)) : new ArrayList<>();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read emails from file: " + file.getPath(), e);
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void addEmails(List<EmailMessage> emailMessages) {
        rwLock.writeLock().lock();
        try {
            List<EmailMessage> userEmails = loadEmails();
            int id = getNextId(userEmails);
            for (EmailMessage email : emailMessages) {
                userEmails.add(new EmailMessage(email.sender(), email.recipients(), email.subject(),
                        email.body(), id++, new Date()));
            }
            saveEmails(userEmails);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    private void saveEmails(List<EmailMessage> emails) {
        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            gson.toJson(emails, writer);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write emails to file: " + file.getPath(), e);
        }
    }

    public void deleteEmails(List<EmailMessage> emails) {
        rwLock.writeLock().lock();
        try {
            List<EmailMessage> userEmails = loadEmails();
            System.out.println("Deleting emails: " + userEmails);
            boolean removed = userEmails.removeAll(emails);
            if (!removed) {
                throw new IllegalArgumentException("Emails to delete not found in file.");
            }
            saveEmails(userEmails);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}

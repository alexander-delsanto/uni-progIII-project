package backend;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileManager {
    private static final Map<String, FileManager> openedFileManager = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final File file;
    //private final Gson gson;

    private FileManager(File file) {
        this.file = file;
    }

    public static synchronized FileManager get(String filename) {
        if (filename == null)
            return null;
        filename = "emails/" + filename + ".json";
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        return openedFileManager.computeIfAbsent(filename, f -> new FileManager(file));
    }
}

package frontend;

import interfaces.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerViewController implements Logger {
    @FXML private ListView<String> logList;
    private final ObservableList<String> logs = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        logList.setItems(logs);
    }

    @FXML
    private void clearLogs() {
        logs.clear();
    }

    @Override
    public synchronized void log(String logMessage) {
        Platform.runLater(() -> {
            logs.add(logMessage);
            logList.scrollTo(logs.size() - 1); // Scroll to the last item
        });
    }

}

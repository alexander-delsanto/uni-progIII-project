package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.Socket;

public class StatusBar {
    private final StringProperty user = new SimpleStringProperty("");
    private final StringProperty status = new SimpleStringProperty("");

    public StringProperty userProperty() {
        return user;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getUser() {
        return user.get();
    }

    public String getStatus() {
        return status.get();
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}

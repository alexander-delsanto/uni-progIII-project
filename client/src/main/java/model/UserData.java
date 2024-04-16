package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {

    private static UserData instance = null;
    public static synchronized UserData getInstance(){
        if(instance == null){
            instance = new UserData();
            instance.setUser("");
            instance.setStatus("");
        }

        return instance;
    }
    private UserData(){}


    private final StringProperty user = new SimpleStringProperty("");
    private final StringProperty status = new SimpleStringProperty("");

    public StringProperty userProperty() {
        return user;
    }

    public String getUser() { return user.get(); }

    public StringProperty statusProperty() { return status; }

    public void setUser(String user) { this.user.set(user); }

    public void setStatus(String status) { this.status.set(status); }
}

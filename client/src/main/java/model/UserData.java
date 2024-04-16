package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {

    private static UserData instance = null;
    public static synchronized UserData getInstance(){
        if(instance == null){
            instance = new UserData();
            instance.setUser("");
        }

        return instance;
    }
    private UserData(){}


    private final StringProperty user = new SimpleStringProperty("");

    public StringProperty userProperty() {
        return user;
    }

    public String getUser() { return user.get(); }

    public void setUser(String user) { this.user.set(user); }
}

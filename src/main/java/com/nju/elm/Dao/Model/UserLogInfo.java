package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class UserLogInfo implements Serializable {

    int ID;
    String email;
    String cancelState;

    public UserLogInfo(int ID, String email, String cancelState) {
        this.ID = ID;
        this.email = email;
        this.cancelState = cancelState;
    }

    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getCancelState() {
        return cancelState;
    }
}

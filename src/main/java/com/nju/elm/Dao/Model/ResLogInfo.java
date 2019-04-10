package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class ResLogInfo implements Serializable {
    int resID;
    String email;
    String password;

    public ResLogInfo(int resID, String email, String password) {
        this.resID = resID;
        this.email = email;
        this.password = password;
    }

    public int getResID() {
        return resID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

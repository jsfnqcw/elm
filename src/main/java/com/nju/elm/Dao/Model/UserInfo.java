package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    int ID;
    String name;
    String phoneNumber;
    int level;
    int exp;

    public UserInfo(int ID, String name, String phoneNumber, int level, int exp) {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.exp = exp;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}

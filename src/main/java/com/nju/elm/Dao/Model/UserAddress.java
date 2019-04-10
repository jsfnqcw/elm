package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class UserAddress implements Serializable {
    String addressString;
    String addressX;
    String addressY;
    int ID;

    public UserAddress(String addressString, String addressX, String addressY, int ID) {
        this.addressString = addressString;
        this.addressX = addressX;
        this.addressY = addressY;
        this.ID = ID;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getAddressX() {
        return addressX;
    }

    public void setAddressX(String addressX) {
        this.addressX = addressX;
    }

    public String getAddressY() {
        return addressY;
    }

    public void setAddressY(String addressY) {
        this.addressY = addressY;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}


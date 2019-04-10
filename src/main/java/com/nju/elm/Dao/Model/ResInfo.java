package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class ResInfo implements Serializable {
    int resID;
    String name;
    String address;
    String addressXY;
    String type;

    public ResInfo(int resID, String name, String address, String addressXY, String type) {
        this.resID = resID;
        this.name = name;
        this.address = address;
        this.addressXY = addressXY;
        this.type = type;
    }

    public int getResID() {
        return resID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressXY() {
        return addressXY;
    }

    public void setAddressXY(String addressXY) {
        this.addressXY = addressXY;
    }

    public void setType(String type) {
        this.type = type;
    }
}

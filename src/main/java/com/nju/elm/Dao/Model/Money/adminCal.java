package com.nju.elm.Dao.Model.Money;

import java.io.Serializable;

public class adminCal implements Serializable {
    int adID;
    String calTime;
    int calOrderId;
    double price;
    int num;

    public int getAdID() {
        return adID;
    }

    public void setAdID(int adID) {
        this.adID = adID;
    }

    public String getCalTime() {
        return calTime;
    }

    public void setCalTime(String calTime) {
        this.calTime = calTime;
    }

    public int getCalOrderId() {
        return calOrderId;
    }

    public void setCalOrderId(int calOrderId) {
        this.calOrderId = calOrderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public adminCal(int adID, String calTime, int calOrderId, double price, int num) {
        this.adID = adID;
        this.calTime = calTime;
        this.calOrderId = calOrderId;
        this.price = price;
        this.num = num;
    }
}

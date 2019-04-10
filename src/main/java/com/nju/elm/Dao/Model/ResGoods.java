package com.nju.elm.Dao.Model;

import java.io.Serializable;

public class ResGoods implements Serializable {
    int ID;
    String name;
    String introduction;
    double price;
    int allowance;
    String startTime;
    String endTime;
    int resID;

    public ResGoods(int ID, String name, String introduction, double price, int allowance, String startTime, String endTime, int resID) {
        this.ID = ID;
        this.name = name;
        this.introduction = introduction;
        this.price = price;
        this.allowance = allowance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resID = resID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAllowance() {
        return allowance;
    }

    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}

package com.nju.elm.Dao.Model.Money;

import java.io.Serializable;

public class resMoney implements Serializable {
    int resID;
    double money;

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public resMoney(int resID, double money) {
        this.resID = resID;
        this.money = money;
    }
}

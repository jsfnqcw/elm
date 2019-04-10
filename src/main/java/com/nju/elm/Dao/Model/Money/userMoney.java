package com.nju.elm.Dao.Model.Money;

import java.io.Serializable;

public class userMoney implements Serializable {
    int userID;
    double money;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public userMoney(int userID, double money) {
        this.userID = userID;
        this.money = money;
    }
}

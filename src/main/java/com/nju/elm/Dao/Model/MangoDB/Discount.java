package com.nju.elm.Dao.Model.MangoDB;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Discount implements Serializable {

    String resID;

    //JsonString
    String discount;

    public Discount(String resID, String discount) {
        this.resID = resID;
        this.discount = discount;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}

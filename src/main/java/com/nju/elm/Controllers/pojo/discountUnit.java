package com.nju.elm.Controllers.pojo;

public class discountUnit {
    int price;
    int dis;

    public discountUnit() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public discountUnit(int price, int dis) {
        this.price = price;
        this.dis = dis;
    }
}

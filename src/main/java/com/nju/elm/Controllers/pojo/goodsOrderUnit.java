package com.nju.elm.Controllers.pojo;

public class goodsOrderUnit {
    String goodsID;
    int num;

    public goodsOrderUnit() {
    }

    public goodsOrderUnit(String goodsID, int num) {
        this.goodsID = goodsID;
        this.num = num;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

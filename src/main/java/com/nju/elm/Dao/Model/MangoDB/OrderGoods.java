package com.nju.elm.Dao.Model.MangoDB;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class OrderGoods implements Serializable {
    String orderID;

    //被序列化后的Goods对象
    String goods;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public OrderGoods(String orderID, String goods) {
        this.orderID = orderID;
        this.goods = goods;
    }
}

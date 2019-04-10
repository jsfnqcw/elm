package com.nju.elm.Dao.Model;


import java.io.Serializable;

public class goodsOrder implements Serializable {

    int orderID;
    int userID;
    int resID;
    String orderTime;
    String arrivalTime;
    double price;
    String address;
    String addressXY;
    String payType;
    String State;

    public goodsOrder(int orderID, int userID, int resID, String orderTime, String arrivalTime, double price, String address, String addressXY, String payType, String state) {
        this.orderID = orderID;
        this.userID = userID;
        this.resID = resID;
        this.orderTime = orderTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.address = address;
        this.addressXY = addressXY;
        this.payType = payType;
        State = state;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    @Override
    public String toString() {
        return "goodsOrder{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", resID=" + resID +
                ", orderTime='" + orderTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", addressXY='" + addressXY + '\'' +
                ", payType='" + payType + '\'' +
                ", State='" + State + '\'' +
                '}';
    }
}

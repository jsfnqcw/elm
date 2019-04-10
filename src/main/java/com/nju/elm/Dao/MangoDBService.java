package com.nju.elm.Dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.elm.Dao.Model.MangoDB.Discount;
import com.nju.elm.Dao.Model.MangoDB.OrderGoods;
import com.nju.elm.Dao.Model.MangoDB.RegisterQueue;
import com.nju.elm.SpringContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class MangoDBService {


    private MongoTemplate mongoTemplate;


    public void saveRegisterQueue(RegisterQueue Queue){
        mongoTemplate.save(Queue);
    }

    public RegisterQueue findRegisterQueue(String email){
        Query query=new Query(Criteria.where("email").is(email));
        RegisterQueue Queue =  mongoTemplate.findOne(query , RegisterQueue.class);
        return Queue;
    }

    public boolean deleteRegisterQueue(String email){
        try {
            Query query=new Query(Criteria.where("email").is(email));
            mongoTemplate.remove(query,RegisterQueue.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void saveDiscount(Discount discount){ mongoTemplate.save(discount); }

    public Discount findDiscount(int resID){
        Query query=new Query(Criteria.where("resID").is(resID+""));
        Discount discount =  mongoTemplate.findOne(query,Discount.class);
        return discount;
    }

    public void addDiscount(int resID,int price,int dis){
        Discount discount = findDiscount(resID);
        deleteDiscount(resID);
        JSONArray array = new JSONArray();

        if(discount == null){
            discount = new Discount(resID+"","");
        }else {
            array = JSON.parseArray(discount.getDiscount());
        }
        JSONObject obj = new JSONObject();
        obj.put("price",price);obj.put("dis",dis);
        array.add(obj);
        discount.setDiscount(array.toJSONString());
        saveDiscount(discount);
    }

    public boolean deleteDiscount(int resID){
        try {
            Query query=new Query(Criteria.where("resID").is(resID+""));
            mongoTemplate.remove(query,Discount.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void saveOrderGoods(OrderGoods orderGoods){ mongoTemplate.save(orderGoods); }

    public OrderGoods findOrderGoods(String orderId){
        Query query=new Query(Criteria.where("orderID").is(orderId));
        OrderGoods OrderGoods =  mongoTemplate.findOne(query , OrderGoods.class);
        return OrderGoods;
    }

    public boolean deleteOrderGoods(String orderId){
        try {
            Query query=new Query(Criteria.where("orderID").is(orderId));
            mongoTemplate.remove(query,OrderGoods.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean example(String email){
        try {



            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //单例模式相关代码
    private MangoDBService() {
        mongoTemplate = SpringContext.getBean(MongoTemplate.class);
    }

    private static class SingletonFactory {
        private static MangoDBService instance = new MangoDBService();
    }

    public static MangoDBService getInstance() {
        return SingletonFactory.instance;
    }

}

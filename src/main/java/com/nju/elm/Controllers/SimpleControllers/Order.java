package com.nju.elm.Controllers.SimpleControllers;


import com.alibaba.fastjson.JSON;
import com.nju.elm.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Order {
    @Autowired
    private OrderService service;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/user/getOrders")
    @ResponseBody
    public String getOrders() {
        int id = (int)(request.getSession().getAttribute("id"));
        String result = JSON.toJSONString(service.getOrdersByUser(id));
        return result;
    }

    @RequestMapping("/store/getOrders")
    @ResponseBody
    public String getOrdersInRes() {
        int id = (int)(request.getSession().getAttribute("resID"));
        String result = JSON.toJSONString(service.getOrdersByRes(id));
        return result;
    }

    @RequestMapping("/user/getOrderResTypes")
    @ResponseBody
    public String getOrderResTypes() {
        int id = (int)(request.getSession().getAttribute("id"));
        String result = JSON.toJSONString(service.getOrderResTypes(id));
        return result;
    }


    @RequestMapping("/user/getSelectedOrders")
    @ResponseBody
    public String getSelectedOrders(@RequestParam("time") int time,
                                    @RequestParam("price") int price,
                                    @RequestParam("type") String type) {
        int id = (int)(request.getSession().getAttribute("id"));
        String result = JSON.toJSONString(service.getOrdersByUserSelected(id,time,price,type));
        return result;
    }

    @RequestMapping("/store/getOrderUser")
    @ResponseBody
    public String getOrderUser() {
        int id = (int)(request.getSession().getAttribute("resID"));
        String result = JSON.toJSONString(service.getOrderUser(id));
        return result;
    }

    @RequestMapping("/store/getSelectedOrders")
    @ResponseBody
    public String getSelectedOrders2(@RequestParam("time") int time,
                                    @RequestParam("price") int price,
                                    @RequestParam("user") String user) {
        int id = (int)(request.getSession().getAttribute("resID"));
        String result = JSON.toJSONString(service.getOrdersByResSelected(id,time,price,user));
        return result;
    }











    @RequestMapping("/user/newOrder")
    @ResponseBody
    public String newOrder(@RequestParam("resID") int resID,
                           @RequestParam("shoppingBag") String shoppingBag,
                           @RequestParam("addressString") String addressString,
                           @RequestParam("addressXY") String addressXY) {
        int id = (int)(request.getSession().getAttribute("id"));
        return service.newOrder(id,resID,shoppingBag,addressString,addressXY);
    }














    @RequestMapping("/order/getOrderGood")
    @ResponseBody
    public String getOrderGood(@RequestParam("orderID") int orderID) {
        return service.getOrderGoods(orderID+"");
    }

    @RequestMapping("/order/pay")
    @ResponseBody
    public String pay(@RequestParam("orderID") int orderID) {
        int userID = (int)(request.getSession().getAttribute("id"));
        return service.payOrder(orderID,userID);
    }

    @RequestMapping("/order/cancel")
    @ResponseBody
    public String cancel(@RequestParam("orderID") int orderID) {
        int userID = (int)(request.getSession().getAttribute("id"));
        return service.cancelOrder(orderID,userID);
    }

    @RequestMapping("/order/confirm")
    @ResponseBody
    public String confirm(@RequestParam("orderID") int orderID) {
        int userID = (int)(request.getSession().getAttribute("id"));
        return service.confirmOrder(orderID,userID);
    }


}

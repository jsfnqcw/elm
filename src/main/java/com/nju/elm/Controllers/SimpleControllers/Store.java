package com.nju.elm.Controllers.SimpleControllers;

import com.alibaba.fastjson.JSON;
import com.nju.elm.Dao.Model.ResExamine;
import com.nju.elm.Dao.Model.ResGoods;
import com.nju.elm.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Store {

    @Autowired
    private StoreService service;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/store/send")
    @ResponseBody
    public String send(@RequestParam("email") String email) {
        return service.sendVerificationCode(email)?"Done":"Error";
    }

    @RequestMapping("/store/register")
    @ResponseBody
    public String register(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("code") String code)
    {
        return service.Register(email,password,code);
    }
    @RequestMapping("/store/login")
    @ResponseBody
    public String login(@RequestParam("account") int account,
                        @RequestParam("password") String password)
    {
        return service.login(account,password,request)?"Done":"Error";
    }




    @RequestMapping("/store/info")
    @ResponseBody
    public String getInfo() {
        int resID = (int)(request.getSession().getAttribute("resID"));
        return JSON.toJSONString(service.getInfo(resID));
    }
    @RequestMapping("/store/modifyInfo")
    @ResponseBody
    public String modify(@RequestParam("name") String name,
                         @RequestParam("type") String type,
                         @RequestParam("address") String address,
                         @RequestParam("addressXY") String addressXY)
    {
        int resID = (int)(request.getSession().getAttribute("resID"));
        ResExamine info = new ResExamine(resID,name,address,addressXY,type);
        return service.applyForModifyInfo(info)?"Done":"Error";
    }




    @RequestMapping("/store/getGoods")
    @ResponseBody
    public String getGoods() {
        int resID = (int)(request.getSession().getAttribute("resID"));
        return  JSON.toJSONString(service.getGoodsByRes(resID));
    }

    @RequestMapping("/store/releaseGoods")
    @ResponseBody
    public String releaseGoods(@RequestParam("name") String name,
                               @RequestParam("introduction") String introduction,
                               @RequestParam("price") double price,
                               @RequestParam("allowance") int allowance,
                               @RequestParam("startTime") String startTime,
                               @RequestParam("endTime") String endTime)
    {
        int resID = (int)(request.getSession().getAttribute("resID"));
        ResGoods goods = new ResGoods(0,name,introduction,price,allowance,startTime,endTime,resID);
        return service.addGoods(goods)?"Done":"Error";
    }
    @RequestMapping("/store/deleteGoods")
    @ResponseBody
    public String deleteGoods(@RequestParam("goodsId") int goodsId) {
        return service.delGoods(goodsId)?"Done":"Error";
    }



    @RequestMapping("/store/getDiscount")
    @ResponseBody
    public String getDiscount() {
        int resID = (int)(request.getSession().getAttribute("resID"));
        return service.getDiscount(resID);
    }
    @RequestMapping("/store/delDiscount")
    @ResponseBody
    public String delDiscount() {
        int resID = (int)(request.getSession().getAttribute("resID"));
        return service.delDiscount(resID)?"Done":"Error";
    }
    @RequestMapping("/store/addDiscount")
    @ResponseBody
    public String addDiscount(@RequestParam("price") int price,
                               @RequestParam("dis") int dis)
    {
        int resID = (int)(request.getSession().getAttribute("resID"));
        return service.addDiscount(resID,price,dis)?"Done":"Error";
    }



    @RequestMapping("/store/stores")
    @ResponseBody
    public String stores()
    {
        return  JSON.toJSONString(service.getStores());
    }


}

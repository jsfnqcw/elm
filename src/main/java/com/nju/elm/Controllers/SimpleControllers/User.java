package com.nju.elm.Controllers.SimpleControllers;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nju.elm.Dao.MangoDBService;
import com.nju.elm.Service.MoneyService;
import com.nju.elm.Service.StoreService;
import com.nju.elm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * USER控制器
 *
 * @author: @Quartz
 * @create: 2019-01-27- 20:07
 */
@Controller
public class User {

    @Autowired
    private UserService service;
    @Autowired
    private StoreService storeService;
    @Autowired
    private MoneyService moneyService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/user/send")
    @ResponseBody
    public String send(@RequestParam("email") String email) {
        return service.sendVerificationCode(email)?"Done":"Error";
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public String register(@RequestParam("email") String email,
                           @RequestParam("code") String code)
    {
        return service.Register(email,code)?"Done":"Error";
    }

    @RequestMapping("/user/login")
    @ResponseBody
    public String login(@RequestParam("email") String email,
                        @RequestParam("code") String code)
    {
        return service.login(email,code,request)?"Done":"Error";
    }

    @RequestMapping("/user/cancel")
    @ResponseBody
    public String cancel(@RequestParam("email") String email) {
        return service.cancel(email,request)?"Done":"Error";
    }

    @RequestMapping("/user/modify")
    @ResponseBody
    public String modify(@RequestParam("name") String name,
                         @RequestParam("phoneNumber") String phoneNumber)
    {
        return service.modifyInfo(name,phoneNumber,request)?"Done":"Error";
    }

    @RequestMapping("/user/addAddress")
    @ResponseBody
    public String addAddress(@RequestParam("addressString") String address,
                             @RequestParam("addressXY") String XY)
    {
        int id = (int)(request.getSession().getAttribute("id"));
        return service.addAddress(address,XY,id)?"Done":"Error";
    }

    @RequestMapping("/user/delAddress")
    @ResponseBody
    public String delAddress(@RequestParam("addressString") String address) {
        int id = (int)(request.getSession().getAttribute("id"));
        return service.delAddress(address,id)?"Done":"Error";
    }

    @RequestMapping("/user/getAddress")
    @ResponseBody
    public String getAddress() {
        int id = (int)(request.getSession().getAttribute("id"));
        return service.getAddress(id);
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public String getInfo() {
        int id = (int)(request.getSession().getAttribute("id"));
        String result = JSON.toJSONString(service.getUserInfo(id));
        return result;
    }



    @RequestMapping("/user/getGoods")
    @ResponseBody
    public String getGoods(@RequestParam("resID") int resID) {
        return JSON.toJSONString(storeService.getGoodsByRes(resID));
    }


    @RequestMapping("/user/getDiscounts")
    @ResponseBody
    public String getDiscounts(@RequestParam("resID") int resID) {
        return MangoDBService.getInstance().findDiscount(resID).getDiscount();
    }


    @RequestMapping("/money/new")
    @ResponseBody
    public String newMoney() {
        int id = (int)(request.getSession().getAttribute("id"));
        return moneyService.newUserWith100Money(id)?"Done":"Error";
    }




}
package com.nju.elm.Controllers.SimpleControllers;


import com.alibaba.fastjson.JSON;
import com.nju.elm.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Admin {
    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/admin/getResExm")
    @ResponseBody
    public String getResExm() {
        return JSON.toJSONString(adminService.getResExm());
    }

    @RequestMapping("/admin/confirmExm")
    @ResponseBody
    public String confirmExm(@RequestParam("resID") int resID) {
        return adminService.confirm(resID)?"Done":"Error";
    }

    @RequestMapping("/admin/denyExm")
    @ResponseBody
    public String denyExm(@RequestParam("resID") int resID) {
        return adminService.deny(resID)?"Done":"Error";
    }

    @RequestMapping("/admin/changePer")
    @ResponseBody
    public String changePer(@RequestParam("per") double per) {
        return adminService.changePer(per)?"Done":"Error";
    }

    @RequestMapping("/admin/getNumUsers")
    @ResponseBody
    public String getNumUsers() {
        return adminService.numOfUsers();
    }
    @RequestMapping("/admin/getNumRes")
    @ResponseBody
    public String getNumRes() {
        return adminService.numOfRes();
    }
    @RequestMapping("/admin/finance")
    @ResponseBody
    public String finance() {
        return JSON.toJSONString(adminService.finance());
    }




    @RequestMapping("/admin/login")
    @ResponseBody
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password)
    {
        return adminService.login(account,password,request)?"Done":"Error";
    }


}

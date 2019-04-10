package com.nju.elm.Controllers.SimpleControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导航控制器
 *
 * @author: @Quartz
 * @create: 2019-02-03- 00:28
 */
@Controller
public class Navigate {

    @RequestMapping("/")
    public String send() {
        return "redirect:user/login.html";
    }


}
package javaweb.remember.controller;
import javaweb.remember.utils.GetIpAddress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Time     : 2020-04-14 23:23:38
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : get和post测试
 * File     : HelloWorld.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */


@RestController
public class HelloWorld {

    @GetMapping("/")
    public String hello(
            HttpServletResponse httpServletResponse,
            HttpServletRequest request,
            @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        String  browserDetails  =   request.getHeader("User-Agent");
        System.out.println(browserDetails);
        String ip_info = GetIpAddress.getIp(request);
        System.out.println(ip_info);
        HttpSession session = request.getSession();
          System.out.println(session);
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    System.out.println("准备进数据库");
//                    User user = userMapper.findByToken(token); //去数据库寻找该token值的用户信息
//                    System.out.println(user.toString());
//                    if(user != null){ //若找到了这个用户信息
//                        //写进session，让页面去展示
//                        request.getSession().setAttribute("user",user);
//                    }
                    break;
                }
            }
        }

//        return "index";
        Cookie cookie = new Cookie("token", "token");
        httpServletResponse.addCookie(cookie);
        return String.format("Hello %s!", name);
    }

    @PostMapping("/")
    public String hello2(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


}


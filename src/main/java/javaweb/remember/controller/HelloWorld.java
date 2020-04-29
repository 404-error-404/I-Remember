package javaweb.remember.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping("/")
    public String hello2(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


}


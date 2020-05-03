package javaweb.remember.controller;

import javaweb.remember.entity.User;
import javaweb.remember.entity.VerifyCode;
import javaweb.remember.repository.UserRepository;
import javaweb.remember.repository.VerifyCodeRepository;
import javaweb.remember.service.PhotoService;
import javaweb.remember.service.VerifyCodeService;
import javaweb.remember.utils.MD5Encode;
import javaweb.remember.utils.RandomNumber;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Time     : 2020-04-29 20:52:56
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 用户相关请求
 * File     : UserController.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerifyCodeRepository verifyCodeRepository;
    @Autowired
    VerifyCodeService verifyCodeService;
    @Autowired
    PhotoService photoService;


    /**
     * 通过接收email参数发送验证码
     * @param email 待收取验证码的邮箱
     * @return 返回Json格式结果
     * */
    @CrossOrigin
    @PostMapping("/verify_code/")
    public String send_verify_code(@RequestParam(value = "email") String email) throws JSONException {
        // 待返回的结果
        JSONObject result = new JSONObject();
        // 生成验证码
        String strCode = RandomNumber.createRandomCode(6);
        // 保存到数据库
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(strCode);
        verifyCode.setEmail(email);
        verifyCode.setUpdateTime(new Date());
        // 预先设定失败的返回结果
        result.put("StatusCode", -1);
        result.put("Messenger", "验证码发送失败");
        try {
            // 写入数据库
            verifyCodeRepository.save(verifyCode);

            // 发送邮件
            if(verifyCodeService.sendMail(email, "I Remember", "欢迎您使用I Remember，您本次的验证码是" + strCode + "，验证码5分钟内有效，请及时使用。") == 1){
                result.put("Messenger", "验证码发送成功，5分钟内有效");
                result.put("StatusCode", 0);
            }
            return result.toString();
        } catch (Exception e) {
//            e.printStackTrace();
            return result.toString();
        }
    }

    /**
     * 注册新用户
     * @param email 邮箱
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @return json格式返回值
     */
    @PostMapping(value = "/signup/")
    public String userSignUp(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "code") String code
    ) throws Exception {
        // 待返回的结果
        JSONObject result = new JSONObject();
        // 检查验证码是否正确
        VerifyCode verifyCode = verifyCodeRepository.findByEmail(email);
        Date now = new Date();
        // 核对是否已经获取验证码
        if (verifyCode == null){
            result.put("StatusCode", -2);
            result.put("Messenger", "请先获取验证码");
        }
        // 核对验证码是否有效
        else if (
                ! (verifyCode.getUpdateTime().before(now)
                        &&
                        verifyCode.getUpdateTime().after(new Date(now.getTime() - 300000)))
        ){
            result.put("StatusCode", -3);
            result.put("Messenger", "验证码已失效，请重新获取");
        }
        // 核对验证码是否正确
        else if (!verifyCode.getCode().equals(code)){
            result.put("StatusCode", -4);
            result.put("Messenger", "验证码错误，请核对后重新输入");
        }
        // 核对该用户是否已注册
        else if (null != userRepository.findByEmail(email)) {
            // 不为空则说明该用户已注册
            result.put("StatusCode", -5);
            result.put("Messenger", "您已注册过iFlag，请直接登录");
        }
        // 保存到数据库
        else {
            // 新建用户
            User user = new User();
            user.setUsername(username);
            user.setPassword(MD5Encode.getMD5Str(password));
            user.setEmail(email);
            user.setPhoto("default.jpg");
            user.setSex("未知");
            userRepository.save(user);

            result.put("StatusCode", 0);
            result.put("Messenger", "注册成功");
        }
        return result.toString();
    }
    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @return json格式返回值
     */
    @PostMapping(value = "/login/")
    public String userLogin(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) throws Exception {
        // 待返回的结果
        JSONObject result = new JSONObject();
        // 检查密码是否正确
        User user = userRepository.findByEmail(email);
        if (user == null){
            result.put("StatusCode", -7);
            result.put("Messenger", "您还没有注册，请先注册");
        }
        else if (user.getEmail().equals(email) && user.getPassword().equals(password)){
            Date now = new Date();
            // 生成token
            String token = MD5Encode.getMD5Str(email + password + new Date().toString());
            // 保存登录时间和token
            user.setLastLogin(now);
            user.setToken(token);
            userRepository.save(user);

            // 获取用户信息
            JSONObject userInfo = new JSONObject();
            userInfo.put("email", user.getEmail());
            userInfo.put("photo", user.getPhoto());
            userInfo.put("sex", user.getSex());
            userInfo.put("username", user.getUsername());
            userInfo.put("token", token);
            // 返回值
            result.put("StatusCode", 0);
            result.put("Messenger", userInfo);
        }
        else {
            result.put("StatusCode", -6);
            result.put("Messenger", "登录失败，请核对密码");
        }
        return result.toString();
    }

    /**
     * @param type 图片类型
     * @return 图片，可以在浏览器直接输入URL访问
     */
    @GetMapping(value = "/image/{type}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(
            @PathVariable("type") String type) throws Exception {
        return photoService.getImage(type, "test");
    }
}

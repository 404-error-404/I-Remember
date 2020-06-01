package javaweb.remember.controller;

import javaweb.remember.entity.User;
import javaweb.remember.enumeration.ResultEnum;
import javaweb.remember.repository.UserRepository;
import javaweb.remember.service.PhotoService;
import javaweb.remember.service.RedisService;
import javaweb.remember.service.VerifyCodeService;
import javaweb.remember.utils.RandomNumber;
import javaweb.remember.vo.ResultVo;
import javaweb.remember.vo.SignUpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import java.util.UUID;

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
@Validated
public class UserController {
    @Value("${email.subject}")
    private String subject;
    @Value("${email.content-start}")
    private String content_start;
    @Value("${email.content-end}")
    private String content_end;

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerifyCodeService verifyCodeService;
    @Autowired
    PhotoService photoService;
    @Autowired
    private RedisService redisService;

    //设置验证码过期时间为5分钟（300秒）
    private static final Long VERIFICATION_CODE_EXPIRE_TIME = 300L;

    //设置用户token过期时间为一个小时（3600秒）
    private static final Long USER_TOKEN_EXPIRE_TIME = 3600L;

    /**
     * 通过接收email参数发送验证码
     *
     * @param email 待收取验证码的邮箱
     * @return 返回Json格式结果
     */
    @PostMapping("/verify_code")
    public ResultVo sendVerifyCode(@RequestParam(value = "email")
                                   @NotNull(message = "邮箱不能为空")
                                   @NotBlank(message = "邮箱不能为空")
                                   @Email(message = "邮箱格式错误") String email) {
        //生成验证码
        String code = RandomNumber.createRandomCode(6);
        try {
            //先删除已有的对应的验证码，然后写进redis数据库，并设置过期时间
            redisService.delete(email);
            redisService.set(email, code);
            redisService.expire(email, VERIFICATION_CODE_EXPIRE_TIME);
            //发送邮件
            if (verifyCodeService.sendMail(email, subject, content_start + code + content_end) == 1) {
                return new ResultVo(ResultEnum.SEND_VERIFICATION_CODE_SUCCESS);
            }
        } catch (Exception e) {
            if (redisService.get(email) != null) {
                redisService.delete(email);
            }
        }
        return new ResultVo(ResultEnum.SEND_VERIFICATION_CODE_FAIL);
    }


    @PostMapping(value = "/sign_up")
    public ResultVo userSignUp(@RequestBody SignUpVo signUpVo) {
        String code = redisService.get(signUpVo.getEmail());
        //用户名已注册
        if (userRepository.findByEmail(signUpVo.getEmail()) != null) {
            return new ResultVo(ResultEnum.HAVE_REGISTERED);
        }
        //验证码失效
        if (code == null) {
            return new ResultVo(ResultEnum.VERIFICATION_CODE_FAILURE);
        }
        //验证码错误
        if (!code.equals(signUpVo.getCode())) {
            return new ResultVo(ResultEnum.VERIFICATION_CODE_INCORRECT);
        }
        //注册成功
        User user = new User();
        user.setEmail(signUpVo.getEmail());
        user.setUsername(signUpVo.getUsername());
        user.setPassword(signUpVo.getPassword());
        userRepository.save(user);

        return new ResultVo(ResultEnum.REGISTER_SUCCESS);
    }

    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return json格式返回值
     */
    @PostMapping(value = "/login")
    public ResultVo userLogin(
            HttpServletResponse httpServletResponse,
            @RequestParam(value = "email")
            @Email @NotBlank(message = "邮箱不能为空")
            @NotNull(message = "邮箱不能为空") String email,
            @RequestParam(value = "password")
            @NotNull(message = "密码不能为空")
            @NotBlank(message = "密码不能为空")
            @Max(value = 20, message = "密码太长啦")
            @Min(value = 6, message = "密码太短啦") String password
    ) {
        User user = userRepository.findByEmail(email);
        if(user==null){
            return new ResultVo(ResultEnum.HAVE_NOT_REGISTERED);
        }
        if(!user.getPassword().equals(password)){
            return new ResultVo(ResultEnum.PASSWORD_INCORRECT);
        }
        String token = UUID.randomUUID().toString();
        //若该用户已登录，删除redis中已有的token，写入新的token，并设置过期时间
        redisService.delete(email);
        redisService.set(user.getId().toString(),token);
        redisService.expire(user.getId().toString(),USER_TOKEN_EXPIRE_TIME);
        ResultVo resultVo = new ResultVo(ResultEnum.LOGIN_SUCCESS);
        resultVo.setData(token);
        return resultVo;
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

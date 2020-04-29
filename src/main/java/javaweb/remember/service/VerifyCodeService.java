package javaweb.remember.service;


import javaweb.remember.entity.VerifyCode;
import javaweb.remember.repository.VerifyCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Time     : 2020-04-21 23:44:23
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 验证码相关服务
 * File     : VerifyCodeService.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
@Service
public class VerifyCodeService {
    @Autowired
    VerifyCodeRepository verifyCodeRepository;
    // 发送邮件
    @Autowired
    private JavaMailSender mailSender;
    /**
     * @param receiver 邮件接收人
     * @param content 邮件内容
     * @param subject 邮件主题
     * @return 1，邮件发送成功；0，邮件发送失败
     * */
    public int sendMail(String receiver, String subject, String content) {
        if(receiver.equals("")){
            return 0;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人信息
            message.setFrom("jiale@icube.fun");
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}

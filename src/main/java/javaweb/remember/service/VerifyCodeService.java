package javaweb.remember.service;

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
public interface VerifyCodeService {

    /**
     * @param receiver 邮件接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 1，邮件发送成功；0，邮件发送失败
     * */
    public int sendMail(String receiver, String subject, String content);
}

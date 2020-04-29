package javaweb.remember.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Time     : 2020-04-21 18:26:23
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 邮箱对应验证码表
 * File     : VerifyCode.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
@Entity     // 注解表示这是个实体类
public class VerifyCode {
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '更新时间'")
    private Date updateTime;//更新时间
    @Id
    private String email;   // 邮箱账号
    private String code;    // 验证码


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

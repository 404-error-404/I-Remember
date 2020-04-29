package javaweb.remember.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Time     : 2020-04-29 20:35:24
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 用户表
 * File     : User.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
@Entity     // 注解表示这是个实体类
public class User {
    @Id
    private String email;
    private String password;
    private String username;
    private String sex;
    private String photo;
    private String token;
    private Date lastLogin;

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getToken() {
        return token;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSex() {
        return sex;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

package javaweb.remember.repository;


import javaweb.remember.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Time     : 2020-04-21 19:28:42
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 用户表相关操作
 * File     : UserRepository.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    // 根据用户邮箱查询数据
    User findByEmail(String email);
    // 根据token查询
    User findByToken(String token);
}
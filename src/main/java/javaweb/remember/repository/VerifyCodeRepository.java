package javaweb.remember.repository;


import javaweb.remember.entity.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Time     : 2020-04-21 23:42:55
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 验证码表相关操作
 * File     : VerifyCodeRepository.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, String>, JpaSpecificationExecutor<VerifyCode> {
    // 根据用户邮箱查询数据
    VerifyCode findByEmail(String email);

}

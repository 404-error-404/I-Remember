package javaweb.remember.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Time     : 2020-04-24 12:56:09
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : MD5加密函数
 * File     : MD5Encode.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
public class MD5Encode {
    public static String getMD5Str(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        }
        catch (Exception e) {
            return "";
        }
    }
}

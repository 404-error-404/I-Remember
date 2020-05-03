package javaweb.remember.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

/**
 * Time     : 2020-04-30 20:06:34
 * Author   : 张佳乐
 * E-mail   : 1791781644@qq.com
 * Remarks  : 图片相关服务
 * File     : PhotoService.java
 * Project  : I-Remember
 * Software : IntelliJ IDEA
 * Copyright © 2020 张佳乐. All rights reserved.
 */
@Service
public class PhotoService {
    /**
     * 获取图片
     * @param type 图片类型，对应头像或其他
     * @param fileName 图片名字
     * @return 图片的bytes数组
     * @throws Exception 图片类型不正确
     */
    public byte[] getImage(
            String type, String fileName
    ) throws Exception {
        // -------------拼接图片保存位置并核对图片存在-------------
        // 注意路径拼接的时候使用'/'，不然会导致Linux系统下出问题
        if (fileName.equals("")) {
            fileName = "default.jpg";
        }
        String imageRootPath = System.getProperty("user.dir") + "/image/";
        String filePath = imageRootPath + type + "/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            // 设置成默认头像
            filePath = imageRootPath + type + "/default.jpg";
            file = new File(filePath);
            if (!file.exists()){
                throw new Exception("图片不存在");
            }
        }
        // -------------读取头像并返回-------------
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        int readResult = inputStream.read(bytes, 0, inputStream.available());
        if (readResult >= 0){
            return bytes;
        }
        else {
            throw new Exception("图片读取出错，图片类型：" + type);
        }
    }
}

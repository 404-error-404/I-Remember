package javaweb.remember.controller;

import javaweb.remember.entity.Memory;
import javaweb.remember.enumeration.ResultEnum;
import javaweb.remember.service.MemoryService;
import javaweb.remember.service.RedisService;
import javaweb.remember.utils.DataBaseArrayUtils;
import javaweb.remember.utils.ImageNameUtils;
import javaweb.remember.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @PostMapping("/saveMemory")
    public ResultVo save(@RequestParam("memoryName") String title,
                         @RequestParam("labelList") String[] tags,
                         @RequestParam("content") String content,
                         @RequestParam("images") MultipartFile[] images,
                         HttpServletRequest request){

        ResultVo resultVo = new ResultVo();
        Map<String, Object> memoryId = new HashMap<>();
        String[] allImages = new String[images.length];
        Long userId = (Long)request.getAttribute("id");
        String path = System.getProperty("user.dir") + "/image/";
        int num = 0;

        //图片储存
        for(MultipartFile image:images){
            String newImageName = ImageNameUtils.reFileName(image.getOriginalFilename(), userId);
            File dest = new File(path + "/" + newImageName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            try {
                image.transferTo(dest); //保存文件
                allImages[num] = newImageName;
                num++;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return new ResultVo(-100, e.getMessage(), null);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResultVo(-100, e.getMessage(), null);
            }
        }

        //数据存入数据库
        Memory m = new Memory();
        Memory m2;

        m.setTags(DataBaseArrayUtils.ArrayToString(tags));
        m.setTitle(title);
        m.setContent(content);
        m.setCreateTime(new Date(new java.util.Date().getTime()));
        m.setCreator(userId);
        m.setImages(DataBaseArrayUtils.ArrayToString(allImages));
        m2 = memoryService.save(m);

        if(m2 != null){
            resultVo = new ResultVo(ResultEnum.REMEMBER_PUBLISH_SUCCESS);
            memoryId.put("memoryID",m2.getId());
            resultVo.setData(memoryId);
        }
        else{
            resultVo = new ResultVo(ResultEnum.REMEMBER_PUBLISH_FAIL);
        }
        return resultVo;
    }


    @PostMapping("/memoryShow")
    public ResultVo memoryShow(@RequestParam("memoryID") Long memoryId){

        Map<String,Object> memory = new HashMap<>();
        ResultVo resultVo;
        Memory m = memoryService.findById(memoryId);
        if(m == null){
            resultVo = new ResultVo(ResultEnum.MEMORY_SHOW_FAIL);
            return resultVo;
        }

        memory.put("name",m.getTitle());
        memory.put("labelList",DataBaseArrayUtils.StringToArray(m.getTags()));
        memory.put("content",m.getContent());
        memory.put("imgUrls",DataBaseArrayUtils.StringToArray(m.getImages()));
        memory.put("time",m.getCreateTime());

        resultVo = new ResultVo(ResultEnum.MEMORY_SHOW_SUCCESS);
        resultVo.setData(memory);
        return resultVo;
    }
}

//Author:刘行
package javaweb.remember.scheduler;

import javaweb.remember.repository.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 每天0点0分0秒进行记忆检索，
 */
@Component
public class MemoryScheduler {
    @Autowired
    MemoryRepository memoryRepository;

    @Scheduled(cron = "0 0 0 ? * *")
    @Transactional
    public void memoryDisappear(){
        String nowDate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        memoryRepository.memoryDisappear(nowDate);
    }
}

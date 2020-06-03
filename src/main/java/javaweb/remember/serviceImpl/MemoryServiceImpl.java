//Author:刘行
package javaweb.remember.serviceImpl;

import javaweb.remember.entity.Memory;
import javaweb.remember.repository.MemoryRepository;
import javaweb.remember.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoryServiceImpl implements MemoryService {

    @Autowired
    private MemoryRepository memoryRepository;

    @Override
    public Memory save(Memory memory){return memoryRepository.save(memory);}
}

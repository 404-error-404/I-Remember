package javaweb.remember.repository;

import javaweb.remember.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<Memory,Long> {

    @Modifying
    @Query(value = "delete from Memory memory where memory.createTime < ?1")
    void memoryDisappear(String date);
}

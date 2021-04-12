package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.ConsumeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface ConsumeMapper {
    List<ConsumeEntity> findAll();
    ConsumeEntity findById(int id);
    List<ConsumeEntity> findBySid(String sid);
    List<ConsumeEntity> findByDate(Date date);
    List<ConsumeEntity> findByStoreId(int storeId);
    int insertConsume(ConsumeEntity consumeEntity);
    int updateConsume(ConsumeEntity consumeEntity);
    int deleteConsume(int id);
}

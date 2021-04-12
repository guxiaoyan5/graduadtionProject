package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.store.StoreEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreMapper {
    StoreEntity findById(int id);
    List<StoreEntity> findAll();
    int insertStore(StoreEntity storeEntity);
    int updateStore(StoreEntity storeEntity);
    int deleteStore(int id);
}

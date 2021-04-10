package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StoreEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    StoreEntity findById(int id);
    List<StoreEntity> findAll();
}

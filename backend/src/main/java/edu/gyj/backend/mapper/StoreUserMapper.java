package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StoreUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreUserMapper {
    StoreUserEntity findByName(String name);
    List<StoreUserEntity> findAll();
}

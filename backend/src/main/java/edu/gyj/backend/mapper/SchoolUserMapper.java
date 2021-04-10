package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.SchoolUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchoolUserMapper {
    SchoolUserEntity findByName(String name);
    List<SchoolUserEntity> findAll();
}

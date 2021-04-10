package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StudentUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentUserMapper {
    StudentUserEntity findByName(String name);

    List<StudentUserEntity> findAll();
}

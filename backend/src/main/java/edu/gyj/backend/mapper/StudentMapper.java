package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    StudentEntity findById(String id);
    List<StudentEntity> findAll();
}

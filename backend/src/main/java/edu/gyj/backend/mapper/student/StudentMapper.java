package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    StudentEntity findById(String id);
    List<StudentEntity> findAll();
}

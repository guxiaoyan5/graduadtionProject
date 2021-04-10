package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StudentDayCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDayCSMapper {
    StudentDayCSEntity findById(int id);
    List<StudentDayCSEntity> findAll();
}

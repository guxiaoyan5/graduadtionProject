package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentTCSMapper {
    List<StudentTCSEntity> findAll();

    List<StudentTCSEntity> findBySid(String sid);
}

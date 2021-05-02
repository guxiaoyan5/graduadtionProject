package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentDayCSEntity;
import edu.gyj.backend.entity.student.StudentDayTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface StudentDayTCSMapper {
    List<StudentDayTCSEntity> findAll();

    List<StudentDayTCSEntity> findBySid(String sid);

    List<StudentDayTCSEntity> findByDate(Date date);
}

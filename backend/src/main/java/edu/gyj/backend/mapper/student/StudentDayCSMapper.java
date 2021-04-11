package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentDayCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface StudentDayCSMapper {
    List<StudentDayCSEntity> findAll();

    List<StudentDayCSEntity> findBySid(String sid);


    List<StudentDayCSEntity> findByDate(Date date);
}

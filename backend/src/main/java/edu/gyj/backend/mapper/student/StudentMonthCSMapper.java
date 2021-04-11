package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentDayCSEntity;
import edu.gyj.backend.entity.student.StudentMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface StudentMonthCSMapper {
    List<StudentMonthCSEntity> findAll();

    List<StudentMonthCSEntity> findBySid(String sid);

    List<StudentMonthCSEntity> findByName(String name);

    List<StudentMonthCSEntity> findByMonth(int month,int year);

    List<StudentMonthCSEntity> findByYear(int year);
}

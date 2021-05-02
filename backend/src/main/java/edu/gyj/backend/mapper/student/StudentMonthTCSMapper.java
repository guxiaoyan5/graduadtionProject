package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentMonthCSEntity;
import edu.gyj.backend.entity.student.StudentMonthTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentMonthTCSMapper {
    List<StudentMonthTCSEntity> findAll();

    List<StudentMonthTCSEntity> findBySid(String sid);

    List<StudentMonthTCSEntity> findByMonth(int month,int year);

    List<StudentMonthTCSEntity> findByYear(int year);
}

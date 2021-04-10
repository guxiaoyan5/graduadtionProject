package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StudentMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMonthCSMapper {
    StudentMonthCSEntity findById(int id);
    List<StudentMonthCSEntity> findAll();
}

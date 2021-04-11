package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentCSEntity;
import edu.gyj.backend.entity.student.StudentMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentCSMapper {
    List<StudentCSEntity> findAll();

    List<StudentCSEntity> findBySid(String sid);


}

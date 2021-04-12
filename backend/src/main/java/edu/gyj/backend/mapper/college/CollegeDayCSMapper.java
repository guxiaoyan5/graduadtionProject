package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.major.MajorDayCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface CollegeDayCSMapper {
    List<CollegeDayCSEntity> findAll();
    List<CollegeDayCSEntity> findByCollegeId(int id);
    List<CollegeDayCSEntity> findByDate(Date date);
}

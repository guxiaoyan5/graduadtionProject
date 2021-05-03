package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.college.CollegeDayTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface CollegeDayTCSMapper {
    List<CollegeDayTCSEntity> findAll();

    List<CollegeDayTCSEntity> findByCollegeId(int id);

    List<CollegeDayTCSEntity> findByDate(Date date);

    List<CollegeDayTCSEntity> findByDates(int id, Date start, Date end);
}

package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorDayCSEntity;
import edu.gyj.backend.entity.major.MajorDayTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface MajorDayTCSMapper {
    List<MajorDayTCSEntity> findAll();
    List<MajorDayTCSEntity> findByMajorId(int id);
    List<MajorDayTCSEntity> findByDate(Date date);
    List<MajorDayTCSEntity> findByDates(int id,Date start,Date end);
}

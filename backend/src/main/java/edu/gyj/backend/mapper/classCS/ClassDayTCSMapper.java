package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import edu.gyj.backend.entity.classCS.ClassDayTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface ClassDayTCSMapper {
    List<ClassDayTCSEntity> findAll();

    List<ClassDayTCSEntity> findByClassId(int id);

    List<ClassDayTCSEntity> findByDate(Date date);

    List<ClassDayTCSEntity> findByDates(int id, Date start, Date end);
}

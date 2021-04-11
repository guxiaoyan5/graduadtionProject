package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface ClassDayCSMapper {
    List<ClassDayCSEntity> findAll();
    List<ClassDayCSEntity> findByClassId(int id);
    List<ClassDayCSEntity> findByDate(Date date);
}

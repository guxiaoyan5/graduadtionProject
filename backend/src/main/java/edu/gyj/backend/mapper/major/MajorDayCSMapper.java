package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import edu.gyj.backend.entity.major.MajorDayCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Mapper
public interface MajorDayCSMapper {
    List<MajorDayCSEntity> findAll();
    List<MajorDayCSEntity> findByMajorId(int id);
    List<MajorDayCSEntity> findByDate(Date date);
}

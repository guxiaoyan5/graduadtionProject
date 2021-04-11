package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMonthCSMapper {
    List<ClassMonthCSEntity> findAll();
    List<ClassMonthCSEntity> findByClassId(int classId);
    List<ClassMonthCSEntity> findByClassName(String name);
    List<ClassMonthCSEntity> findByMonth(int month,int year);
    List<ClassMonthCSEntity> findByYear(int year);
}
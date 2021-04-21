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
    List<ClassMonthCSEntity> findByMonth(int month,int year);
    List<ClassMonthCSEntity> findByYear(int year);
    List<ClassMonthCSEntity> findByClassIdAndYearAndMonth(int classId,int year,int month);
    List<ClassMonthCSEntity> findByClassIdAndYear(int classId,int year);
}

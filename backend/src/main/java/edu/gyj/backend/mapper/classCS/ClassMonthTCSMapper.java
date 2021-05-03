package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import edu.gyj.backend.entity.classCS.ClassMonthTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassMonthTCSMapper {
    List<ClassMonthTCSEntity> findAll();
    List<ClassMonthTCSEntity> findByClassId(int classId);
    List<ClassMonthTCSEntity> findByMonth(int month,int year);
    List<ClassMonthTCSEntity> findByYear(int year);
    List<ClassMonthTCSEntity> findByClassIdAndYearAndMonth(int classId,int year,int month);
    List<ClassMonthTCSEntity> findByClassIdAndYear(int classId,int year);
}

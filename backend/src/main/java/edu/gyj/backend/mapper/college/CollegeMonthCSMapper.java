package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollegeMonthCSMapper {
    List<CollegeMonthCSEntity> findAll();
    List<CollegeMonthCSEntity> findByCollegeId(int collegeId);
    List<CollegeMonthCSEntity> findByMonth(int month, int year);
    List<CollegeMonthCSEntity> findByYear(int year);
    List<CollegeMonthCSEntity> findByCollegeIdAndYear(int collegeId,int year);
    List<CollegeMonthCSEntity> findByCollegeIdAndYearAndMonth(int collegeId,int year,int month);
}

package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.entity.college.CollegeMonthTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollegeMonthTCSMapper {
    List<CollegeMonthTCSEntity> findAll();

    List<CollegeMonthTCSEntity> findByCollegeId(int collegeId);

    List<CollegeMonthTCSEntity> findByMonth(int month, int year);

    List<CollegeMonthTCSEntity> findByYear(int year);

    List<CollegeMonthTCSEntity> findByCollegeIdAndYear(int collegeId, int year);

    List<CollegeMonthTCSEntity> findByCollegeIdAndYearAndMonth(int collegeId, int year, int month);
}

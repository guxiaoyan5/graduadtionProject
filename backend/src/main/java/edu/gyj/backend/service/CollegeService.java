package edu.gyj.backend.service;

import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.college.CollegeMonthCSEntity;

import java.util.Date;
import java.util.List;

public interface CollegeService {
    public List<CollegeEntity> findAll();
    public int add(CollegeEntity collegeEntity);
    public int update(CollegeEntity collegeEntity);
    public int delete(CollegeEntity collegeEntity);
    public List<CollegeMonthCSEntity> findByCollegeIdAndYear(int collegeId,int year);
    public List<CollegeMonthCSEntity> findByCollegeIdAndYearAndMonth(int collegeId,int year,int month);
    public List<CollegeDayCSEntity> findByCollegeIdAndDates(int collegeId, Date start,Date end);
}

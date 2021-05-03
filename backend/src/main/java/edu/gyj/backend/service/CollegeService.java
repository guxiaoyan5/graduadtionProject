package edu.gyj.backend.service;

import edu.gyj.backend.entity.college.*;

import java.util.Date;
import java.util.List;

public interface CollegeService {
    public List<CollegeEntity> findAll();

    public int add(CollegeEntity collegeEntity);

    public int update(CollegeEntity collegeEntity);

    public int delete(CollegeEntity collegeEntity);

    public List<CollegeMonthCSEntity> findByCollegeIdAndYear(int collegeId, int year);

    public List<CollegeMonthCSEntity> findByCollegeIdAndYearAndMonth(int collegeId, int year, int month);

    public List<CollegeDayCSEntity> findByCollegeIdAndDates(int collegeId, Date start, Date end);

    public List<CollegeMonthTCSEntity> findThreeByCollegeIdAndYear(int collegeId, int year);

    public List<CollegeMonthTCSEntity> findThreeByCollegeIdAndYearAndMonth(int collegeId, int year, int month);

    public List<CollegeDayTCSEntity> findThreeByCollegeIdAndDates(int collegeId, Date start, Date end);

    public List<CollegeTCSEntity> findThreeByCollegeId(int collegeId);

    public List<CollegeCSEntity> findByCollegeId(int collegeId);
}

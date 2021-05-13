package edu.gyj.backend.service.impl;

import edu.gyj.backend.bean.LevelDayCSBean;
import edu.gyj.backend.bean.LevelDayTCSBean;
import edu.gyj.backend.bean.LevelMonthCSBean;
import edu.gyj.backend.bean.LevelMonthTCSBean;
import edu.gyj.backend.entity.college.*;
import edu.gyj.backend.mapper.bean.NewCollegeDayCSMapper;
import edu.gyj.backend.mapper.bean.NewCollegeDayTCSMapper;
import edu.gyj.backend.mapper.bean.NewCollegeMonthCSMapper;
import edu.gyj.backend.mapper.bean.NewCollegeMonthTCSMapper;
import edu.gyj.backend.mapper.college.*;
import edu.gyj.backend.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    CollegeMonthCSMapper collegeMonthCSMapper;
    @Autowired
    CollegeDayCSMapper collegeDayCSMapper;
    @Autowired
    CollegeDayTCSMapper collegeDayTCSMapper;
    @Autowired
    CollegeMonthTCSMapper collegeMonthTCSMapper;
    @Autowired
    CollegeCSMapper collegeCSMapper;
    @Autowired
    CollegeTCSMapper collegeTCSMapper;
    @Autowired
    NewCollegeMonthCSMapper newCollegeMonthCSMapper;
    @Autowired
    NewCollegeDayCSMapper newCollegeDayCSMapper;
    @Autowired
    NewCollegeMonthTCSMapper newCollegeMonthTCSMapper;
    @Autowired
    NewCollegeDayTCSMapper newCollegeDayTCSMapper;

    @Override
    public List<CollegeEntity> findAll() {
        return collegeMapper.findAll();
    }

    @Override
    public int add(CollegeEntity collegeEntity) {
        List<CollegeEntity> collegeEntities = this.findAll();
        boolean tag = false;
        for (CollegeEntity c : collegeEntities) {
            if (c.getCollege().compareTo(collegeEntity.getCollege()) == 0) {
                tag = true;
            }
        }
        if (tag) {
            return -1;
        }
        return collegeMapper.insertCollege(collegeEntity);
    }

    @Override
    public int update(CollegeEntity collegeEntity) {
        return collegeMapper.updateCollege(collegeEntity);
    }

    @Override
    public int delete(CollegeEntity collegeEntity) {
        return collegeMapper.deleteCollege(collegeEntity.getId());
    }

    @Override
    public List<CollegeMonthCSEntity> findByCollegeIdAndYear(int collegeId, int year) {
        return collegeMonthCSMapper.findByCollegeIdAndYear(collegeId, year);
    }

    @Override
    public List<CollegeMonthCSEntity> findByCollegeIdAndYearAndMonth(int collegeId, int year, int month) {
        return collegeMonthCSMapper.findByCollegeIdAndYearAndMonth(collegeId, year, month);
    }

    @Override
    public List<CollegeDayCSEntity> findByCollegeIdAndDates(int collegeId, Date start, Date end) {
        return collegeDayCSMapper.findByDates(collegeId, new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
    }

    @Override
    public List<CollegeMonthTCSEntity> findThreeByCollegeIdAndYear(int collegeId, int year) {
        return collegeMonthTCSMapper.findByCollegeIdAndYear(collegeId, year);
    }

    @Override
    public List<CollegeMonthTCSEntity> findThreeByCollegeIdAndYearAndMonth(int collegeId, int year, int month) {
        return collegeMonthTCSMapper.findByCollegeIdAndYearAndMonth(collegeId, year, month);
    }

    @Override
    public List<CollegeDayTCSEntity> findThreeByCollegeIdAndDates(int collegeId, Date start, Date end) {
        return collegeDayTCSMapper.findByDates(collegeId, new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
    }

    @Override
    public List<CollegeTCSEntity> findThreeByCollegeId(int collegeId) {
        return collegeTCSMapper.findByCollegeId(collegeId);
    }

    @Override
    public List<CollegeCSEntity> findByCollegeId(int collegeId) {
        return collegeCSMapper.findByCollegeId(collegeId);
    }

    @Override
    public List<LevelMonthCSBean> findByIdAndMonth(int id, int year, int month) {
        return newCollegeMonthCSMapper.findByIdAndMonth(id, year, month);
    }

    @Override
    public List<LevelDayCSBean> findByIdAndDay(int id, int year, int month, int day) {
        return newCollegeDayCSMapper.findByIdAndDay(id, java.sql.Date.valueOf(year + "-" + month + "-" + day));
    }

    @Override
    public List<LevelMonthTCSBean> findTCSByIdAndMonth(int id, int year, int month) {
        return newCollegeMonthTCSMapper.findByIdAndMonth(id,year,month);
    }

    @Override
    public List<LevelDayTCSBean> findTCSByIdAndDay(int id, int year, int month, int day) {
        return newCollegeDayTCSMapper.findByIdAndDay(id, java.sql.Date.valueOf(year + "-" + month + "-" + day));
    }
}

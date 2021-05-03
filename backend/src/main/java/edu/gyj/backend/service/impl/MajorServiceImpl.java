package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.major.*;
import edu.gyj.backend.mapper.college.CollegeMapper;
import edu.gyj.backend.mapper.major.*;
import edu.gyj.backend.result.MajorResult;
import edu.gyj.backend.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    MajorMonthCSMapper majorMonthCSMapper;
    @Autowired
    MajorDayCSMapper majorDayCSMapper;
    @Autowired
    MajorDayTCSMapper majorDayTCSMapper;
    @Autowired
    MajorMonthTCSMapper majorMonthTCSMapper;

    @Override
    public List<MajorResult> getAll() {
        List<MajorResult> majorResults = new ArrayList<>();
        List<CollegeEntity> collegeEntities = collegeMapper.findAll();
        for (CollegeEntity collegeEntity : collegeEntities) {
            List<MajorEntity> majorEntities = majorMapper.findByCollegeId(collegeEntity.getId());
            for (MajorEntity majorEntity : majorEntities) {
                majorResults.add(new MajorResult(majorEntity.getId(), majorEntity.getMajor(), collegeEntity.getCollege(), collegeEntity.getId()));
            }
        }
        return majorResults;
    }

    @Override
    public List<MajorEntity> getCollegeId(int collegeId) {
        return majorMapper.findByCollegeId(collegeId);
    }

    @Override
    public int addMajor(MajorEntity majorEntity) {
        return majorMapper.insertMajor(majorEntity);
    }

    @Override
    public int update(MajorEntity majorEntity) {
        return majorMapper.updateMajor(majorEntity);
    }

    @Override
    public int delete(MajorEntity majorEntity) {
        return majorMapper.deleteMajor(majorEntity.getId());
    }


    @Override
    public List<MajorMonthCSEntity> findByMajorIdAndYearAndMonth(int majorId, int year, int month) {
        return majorMonthCSMapper.findByMajorIdAndYearAndMonth(majorId, year, month);
    }

    @Override
    public List<MajorMonthCSEntity> findByMajorIdAndYear(int majorId, int year) {
        return majorMonthCSMapper.findByMajorIdAndYear(majorId, year);
    }

    @Override
    public List<MajorDayCSEntity> findByMajorIdAndDates(int majorId, Date start, Date end) {
        return majorDayCSMapper.findByDates(majorId, new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
    }

    @Override
    public List<MajorMonthTCSEntity> findThreeByMajorIdAndYearAndMonth(int majorId, int year, int month) {
        return majorMonthTCSMapper.findByMajorIdAndYearAndMonth(majorId,year,month);
    }

    @Override
    public List<MajorMonthTCSEntity> findThreeByMajorIdAndYear(int majorId, int year) {
        return majorMonthTCSMapper.findByMajorIdAndYear(majorId,year);
    }

    @Override
    public List<MajorDayTCSEntity> findThreeByMajorIdAndDates(int majorId, Date start, Date end) {
        return majorDayTCSMapper.findByDates(majorId,new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
    }
}

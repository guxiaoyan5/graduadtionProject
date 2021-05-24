package edu.gyj.backend.service.impl;

import edu.gyj.backend.bean.LevelDayCSBean;
import edu.gyj.backend.bean.LevelDayTCSBean;
import edu.gyj.backend.bean.LevelMonthCSBean;
import edu.gyj.backend.bean.LevelMonthTCSBean;
import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.major.*;
import edu.gyj.backend.mapper.bean.NewMajorDayCSMapper;
import edu.gyj.backend.mapper.bean.NewMajorDayTCSMapper;
import edu.gyj.backend.mapper.bean.NewMajorMonthCSMapper;
import edu.gyj.backend.mapper.bean.NewMajorMonthTCSMapper;
import edu.gyj.backend.mapper.college.CollegeMapper;
import edu.gyj.backend.mapper.major.*;
import edu.gyj.backend.result.MajorResult;
import edu.gyj.backend.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    @Autowired
    MajorTCSMapper majorTCSMapper;
    @Autowired
    MajorCSMapper majorCSMapper;
    @Autowired
    NewMajorDayCSMapper newMajorDayCSMapper;
    @Autowired
    NewMajorDayTCSMapper newMajorDayTCSMapper;
    @Autowired
    NewMajorMonthCSMapper newMajorMonthCSMapper;
    @Autowired
    NewMajorMonthTCSMapper newMajorMonthTCSMapper;

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
        majorResults.sort(new Comparator<MajorResult>() {
            @Override
            public int compare(MajorResult o1, MajorResult o2) {
                return Integer.compare(o1.getCollegeId(),o2.getCollegeId());
            }
        });
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
        return majorMonthTCSMapper.findByMajorIdAndYearAndMonth(majorId, year, month);
    }

    @Override
    public List<MajorMonthTCSEntity> findThreeByMajorIdAndYear(int majorId, int year) {
        return majorMonthTCSMapper.findByMajorIdAndYear(majorId, year);
    }

    @Override
    public List<MajorDayTCSEntity> findThreeByMajorIdAndDates(int majorId, Date start, Date end) {
        return majorDayTCSMapper.findByDates(majorId, new java.sql.Date(start.getTime()), new java.sql.Date(end.getTime()));
    }

    @Override
    public List<MajorCSEntity> findByMajorId(int majorId) {
        return majorCSMapper.findByMajorId(majorId);
    }

    @Override
    public List<MajorTCSEntity> findThreeByMajorId(int majorId) {
        return majorTCSMapper.findByMajorId(majorId);
    }

    @Override
    public List<LevelMonthCSBean> findByIdAndMonth(int id, int year, int month) {
        return newMajorMonthCSMapper.findByIdAndMonth(id, year, month);
    }

    @Override
    public List<LevelDayCSBean> findByIdAndDay(int id, int year, int month, int day) {
        return newMajorDayCSMapper.findByIdAndDay(id, java.sql.Date.valueOf(year + "-" + month + "-" + day));
    }

    @Override
    public List<LevelMonthTCSBean> findTCSByIdAndMonth(int id, int year, int month) {
        return newMajorMonthTCSMapper.findByIdAndMonth(id, year, month);
    }

    @Override
    public List<LevelDayTCSBean> findTCSByIdAndDay(int id, int year, int month, int day) {
        return newMajorDayTCSMapper.findByIdAndDay(id,java.sql.Date.valueOf(year + "-" + month + "-" + day));
    }
}

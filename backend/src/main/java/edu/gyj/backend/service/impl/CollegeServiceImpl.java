package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.mapper.college.CollegeMapper;
import edu.gyj.backend.mapper.college.CollegeMonthCSMapper;
import edu.gyj.backend.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    CollegeMonthCSMapper collegeMonthCSMapper;

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
    public List<CollegeMonthCSEntity> findByCollegeIdAndYear(int collegeId,int year) {
        return collegeMonthCSMapper.findByCollegeIdAndYear(collegeId,year);
    }

    @Override
    public List<CollegeMonthCSEntity> findByCollegeIdAndYearAndMonth(int collegeId, int year, int month) {
        return collegeMonthCSMapper.findByCollegeIdAndYearAndMonth(collegeId,year, month);
    }
}

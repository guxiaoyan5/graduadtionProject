package edu.gyj.backend.service;

import edu.gyj.backend.entity.major.*;
import edu.gyj.backend.result.MajorResult;

import java.util.Date;
import java.util.List;

public interface MajorService {
    public List<MajorResult> getAll();

    public List<MajorEntity> getCollegeId(int collegeId);

    public int addMajor(MajorEntity majorEntity);

    public int update(MajorEntity majorEntity);

    public int delete(MajorEntity majorEntity);

    public List<MajorMonthCSEntity> findByMajorIdAndYearAndMonth(int majorId, int year, int month);

    public List<MajorMonthCSEntity> findByMajorIdAndYear(int majorId, int year);

    public List<MajorDayCSEntity> findByMajorIdAndDates(int majorId, Date start, Date end);

    public List<MajorMonthTCSEntity> findThreeByMajorIdAndYearAndMonth(int majorId, int year, int month);

    public List<MajorMonthTCSEntity> findThreeByMajorIdAndYear(int majorId, int year);

    public List<MajorDayTCSEntity> findThreeByMajorIdAndDates(int majorId, Date start, Date end);

    public List<MajorCSEntity> findByMajorId(int majorId);

    public List<MajorTCSEntity> findThreeByMajorId(int majorId);
}

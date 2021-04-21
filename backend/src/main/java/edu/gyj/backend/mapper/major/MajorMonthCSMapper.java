package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MajorMonthCSMapper {
    List<MajorMonthCSEntity> findAll();

    List<MajorMonthCSEntity> findByMajorId(int MajorId);

    List<MajorMonthCSEntity> findByMonth(int month, int year);

    List<MajorMonthCSEntity> findByYear(int year);

    List<MajorMonthCSEntity> findByMajorIdAndYearAndMonth(int majorId, int year, int month);

    List<MajorMonthCSEntity> findByMajorIdAndYear(int majorId, int year);
}

package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import edu.gyj.backend.entity.major.MajorMonthTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MajorMonthTCSMapper {
    List<MajorMonthTCSEntity> findAll();

    List<MajorMonthTCSEntity> findByMajorId(int MajorId);

    List<MajorMonthTCSEntity> findByMonth(int month, int year);

    List<MajorMonthTCSEntity> findByYear(int year);

    List<MajorMonthTCSEntity> findByMajorIdAndYearAndMonth(int majorId, int year, int month);

    List<MajorMonthTCSEntity> findByMajorIdAndYear(int majorId, int year);
}

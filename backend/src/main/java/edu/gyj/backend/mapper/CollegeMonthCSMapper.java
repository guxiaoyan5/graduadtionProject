package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.CollegeMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollegeMonthCSMapper {
    CollegeMonthCSEntity findById(int id);
    List<CollegeMonthCSEntity> findAll();
}

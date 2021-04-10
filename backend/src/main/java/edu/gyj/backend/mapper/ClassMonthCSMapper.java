package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.ClassMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMonthCSMapper {
    ClassMonthCSEntity findById(int id);
    List<ClassMonthCSEntity> findAll();
}


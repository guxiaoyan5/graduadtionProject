package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.MajorEntity;
import edu.gyj.backend.entity.MajorMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMonthCSMapper {
    MajorMonthCSEntity findById(int id);
    List<MajorMonthCSMapper> findAll();
}

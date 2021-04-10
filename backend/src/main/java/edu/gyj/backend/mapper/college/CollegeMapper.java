package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollegeMapper {
    CollegeEntity findById(String id);

    List<CollegeEntity> findAll();
}

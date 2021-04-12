package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeCSEntity;
import edu.gyj.backend.entity.major.MajorCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollegeCSMapper {
    List<CollegeCSEntity> findAll();

    List<CollegeCSEntity> findByCollegeId(int id);
}

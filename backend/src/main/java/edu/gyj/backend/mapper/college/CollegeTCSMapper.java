package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeCSEntity;
import edu.gyj.backend.entity.college.CollegeTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollegeTCSMapper {
    List<CollegeTCSEntity> findAll();

    List<CollegeTCSEntity> findByCollegeId(int id);
}

package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollegeMapper {
    CollegeEntity findById(int id);
    List<CollegeEntity> findAll();
    int insertCollege(CollegeEntity collegeEntity);
    int updateCollege(CollegeEntity collegeEntity);
    int deleteCollege(int id);
}

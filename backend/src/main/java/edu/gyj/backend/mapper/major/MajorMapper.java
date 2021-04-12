package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MajorMapper {
    MajorEntity findById(int id);
    List<MajorEntity> findAll();
    List<MajorEntity> findByCollegeId(int collegeId);
    int insertMajor(MajorEntity majorEntity);
    int updateMajor(MajorEntity majorEntity);
    int deleteMajor(int id);
}

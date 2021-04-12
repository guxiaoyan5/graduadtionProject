package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.SchoolUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SchoolUserMapper {
    SchoolUserEntity findById(String id);
    List<SchoolUserEntity> findAll();
    int insertSchoolUser(SchoolUserEntity schoolUserEntity);
    int updateSchoolUser(SchoolUserEntity schoolUserEntity);
    int deleteSchoolUser(String id);
}

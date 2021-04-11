package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.SchoolUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SchoolUserMapper {
    SchoolUserEntity findByName(String id);
    List<SchoolUserEntity> findAll();
    void insertSchoolUser(SchoolUserEntity schoolUserEntity);
    void updateSchoolUser(SchoolUserEntity schoolUserEntity);
    void deleteSchoolUser(String id);
}

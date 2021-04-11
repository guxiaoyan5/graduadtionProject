package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassMapper {
    List<ClassEntity> findAll();
    ClassEntity findById(int id);
    List<ClassEntity> findByMajorId(int majorId);
    void insertClass(ClassEntity classEntity);
    void updateClass(ClassEntity classEntity);
    void deleteClass(int id);
}

package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMapper {
    ClassEntity findById(int id);

    List<ClassEntity> findAll();
}

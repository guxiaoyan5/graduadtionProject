package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ClassCSMapper {
    List<ClassCSEntity> findAll();

    List<ClassCSEntity> findByClassId(int id);
}

package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassCSEntity;
import edu.gyj.backend.entity.classCS.ClassTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassTCSMapper {
    List<ClassTCSEntity> findAll();

    List<ClassTCSEntity> findByClassId(int id);
}

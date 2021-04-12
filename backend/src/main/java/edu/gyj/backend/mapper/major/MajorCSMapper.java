package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.classCS.ClassCSEntity;
import edu.gyj.backend.entity.major.MajorCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MajorCSMapper {
    List<MajorCSEntity> findAll();

    List<MajorCSEntity> findByMajorId(int id);
}

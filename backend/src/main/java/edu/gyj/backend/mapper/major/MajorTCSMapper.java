package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorCSEntity;
import edu.gyj.backend.entity.major.MajorTCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MajorTCSMapper {
    List<MajorTCSEntity> findAll();

    List<MajorTCSEntity> findByMajorId(int id);
}

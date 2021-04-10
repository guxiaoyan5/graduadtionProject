package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    MajorEntity findById(int id);

    List<MajorEntity> findAll();
}

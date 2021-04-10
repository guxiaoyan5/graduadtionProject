package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.MajorEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {
    MajorEntity findById(int id);

    List<MajorEntity> findAll();
}

package edu.gyj.backend.mapper.analysis;

import edu.gyj.backend.entity.analysis.AnalysisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnalysisMapper {
    List<AnalysisEntity> findAll();
}

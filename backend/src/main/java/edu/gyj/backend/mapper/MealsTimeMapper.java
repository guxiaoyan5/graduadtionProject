package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.MealsTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MealsTimeMapper {
    List<MealsTime> findAll();
}

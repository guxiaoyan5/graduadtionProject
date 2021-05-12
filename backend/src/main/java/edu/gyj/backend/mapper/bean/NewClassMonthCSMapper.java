package edu.gyj.backend.mapper.bean;

import edu.gyj.backend.bean.LevelMonthCSBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewClassMonthCSMapper {
    List<LevelMonthCSBean> findAll();

    List<LevelMonthCSBean> findByIdAndMonth(int id, int year, int month);
}

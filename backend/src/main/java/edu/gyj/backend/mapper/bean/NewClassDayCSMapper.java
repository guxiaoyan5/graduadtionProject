package edu.gyj.backend.mapper.bean;

import edu.gyj.backend.bean.LevelDayCSBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface NewClassDayCSMapper {
    List<LevelDayCSBean> findAll();

    List<LevelDayCSBean> findByIdAndDay(int id, Date date);
}

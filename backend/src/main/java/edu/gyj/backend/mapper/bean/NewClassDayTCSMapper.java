package edu.gyj.backend.mapper.bean;

import edu.gyj.backend.bean.LevelDayCSBean;
import edu.gyj.backend.bean.LevelDayTCSBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface NewClassDayTCSMapper {
    List<LevelDayTCSBean> findAll();

    List<LevelDayTCSBean> findByIdAndDay(int id, Date date);
}

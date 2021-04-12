package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.store.StoreDayCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface StoreDayCSMapper {
    List<StoreDayCSEntity> findAll();
    List<StoreDayCSEntity> findByStoreId(int id);
    List<StoreDayCSEntity> findByDate(Date date);
}

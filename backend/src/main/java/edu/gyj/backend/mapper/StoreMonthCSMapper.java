package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.StoreMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMonthCSMapper {
    StoreMonthCSEntity findById(int id);

    List<StoreMonthCSEntity> findAll();
}

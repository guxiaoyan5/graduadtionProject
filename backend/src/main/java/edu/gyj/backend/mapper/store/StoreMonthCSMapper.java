package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.entity.store.StoreMonthCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StoreMonthCSMapper {
    List<StoreMonthCSEntity> findAll();
    List<StoreMonthCSEntity> findByStoreId(int storeId);
    List<StoreMonthCSEntity> findByMonth(int month, int year);
    List<StoreMonthCSEntity> findByYear(int year);
}

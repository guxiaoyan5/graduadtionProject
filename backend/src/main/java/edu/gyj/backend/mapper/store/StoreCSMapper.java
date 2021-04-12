package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.college.CollegeCSEntity;
import edu.gyj.backend.entity.store.StoreCSEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreCSMapper {
    List<StoreCSEntity> findAll();

    List<StoreCSEntity> findByStoreId(int id);
}

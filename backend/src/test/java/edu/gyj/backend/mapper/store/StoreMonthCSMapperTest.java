package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.store.StoreMonthCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreMonthCSMapperTest {
    @Autowired
    StoreMonthCSMapper storeMonthCSMapper;

    @Test
    void findAll() {
        List<StoreMonthCSEntity> storeMonthCSEntities = storeMonthCSMapper.findAll();
        storeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByStoreId() {
        List<StoreMonthCSEntity> storeMonthCSEntities = storeMonthCSMapper.findByStoreId(1);
        storeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        List<StoreMonthCSEntity> storeMonthCSEntities = storeMonthCSMapper.findByMonth(4,2021);
        storeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByYear() {
        List<StoreMonthCSEntity> storeMonthCSEntities = storeMonthCSMapper.findByYear(2021);
        storeMonthCSEntities.forEach(System.out::println);
    }
}
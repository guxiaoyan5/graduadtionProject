package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.store.StoreDayCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreDayCSMapperTest {
    @Autowired
    StoreDayCSMapper storeDayCSMapper;

    @Test
    void findAll() {
        List<StoreDayCSEntity> storeDayCSEntities = storeDayCSMapper.findAll();
        storeDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByStoreId() {
        List<StoreDayCSEntity> storeDayCSEntities = storeDayCSMapper.findByStoreId(1);
        storeDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByDate() {
        List<StoreDayCSEntity> storeDayCSEntities = storeDayCSMapper.findByDate(Date.valueOf("2021-4-1"));
        storeDayCSEntities.forEach(System.out::println);
    }
}
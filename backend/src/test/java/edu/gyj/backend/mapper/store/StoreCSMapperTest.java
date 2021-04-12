package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.store.StoreCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreCSMapperTest {
    @Autowired
    StoreCSMapper storeCSMapper;

    @Test
    void findAll() {
        List<StoreCSEntity>  storeCSEntities = storeCSMapper.findAll();
        storeCSEntities.forEach(System.out::println);
    }

    @Test
    void findByStoreId() {
        List<StoreCSEntity>  storeCSEntities = storeCSMapper.findByStoreId(1);
        storeCSEntities.forEach(System.out::println);
    }
}
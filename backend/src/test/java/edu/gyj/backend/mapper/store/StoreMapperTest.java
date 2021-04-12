package edu.gyj.backend.mapper.store;

import edu.gyj.backend.entity.store.StoreEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreMapperTest {
    @Autowired
    StoreMapper storeMapper;

    @Test
    void findById() {
        StoreEntity storeEntity = storeMapper.findById(1);
        System.out.println(storeEntity);
    }

    @Test
    void findAll() {
        List<StoreEntity> storeEntities = storeMapper.findAll();
        storeEntities.forEach(System.out::println);
    }

    @Test
    void insertStore() {
        System.out.println(storeMapper.insertStore(new StoreEntity(-1,"a")));
    }

    @Test
    void updateStore() {
        System.out.println(storeMapper.updateStore(new StoreEntity(6,"b")));
    }

    @Test
    void deleteStore() {
        System.out.println(storeMapper.deleteStore(6));
    }
}
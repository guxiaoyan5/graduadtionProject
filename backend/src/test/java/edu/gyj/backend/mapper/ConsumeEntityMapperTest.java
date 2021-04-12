package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.ConsumeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

@SpringBootTest
class ConsumeEntityMapperTest {
    @Autowired
    ConsumeMapper consumeMapper;

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        ConsumeEntity consumeEntities = consumeMapper.findById(1);
        System.out.println(consumeEntities);
    }

    @Test
    void findBySid() {
        List<ConsumeEntity> consumeEntities = consumeMapper.findBySid("100001");
        consumeEntities.forEach(System.out::println);
    }

    @Test
    void findByDate() {
        List<ConsumeEntity> consumeEntities = consumeMapper.findByDate(Date.valueOf("2021-4-1"));
        consumeEntities.forEach(System.out::println);
    }

    @Test
    void findByStoreId() {
        List<ConsumeEntity> consumeEntities = consumeMapper.findByStoreId(1);
        consumeEntities.forEach(System.out::println);
    }

    @Test
    void insertConsume() {
        System.out.println(consumeMapper.insertConsume(new ConsumeEntity(-1,"100001",Date.valueOf("2021-4-1"),20,1,"啊哈")));
    }

    @Test
    void updateConsume() {
        System.out.println(consumeMapper.updateConsume(new ConsumeEntity(21,"100001",null,20,1,"啊哈")));
    }

    @Test
    void deleteConsume() {
        System.out.println(consumeMapper.deleteConsume(20));
    }
}
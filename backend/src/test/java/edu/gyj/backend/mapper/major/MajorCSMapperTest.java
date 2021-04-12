package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MajorCSMapperTest {
    @Autowired
    MajorCSMapper majorCSMapper;
    @Test
    void findAll() {
        List<MajorCSEntity> majorCSEntities = majorCSMapper.findAll();
        majorCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        List<MajorCSEntity> majorCSEntities = majorCSMapper.findByMajorId(1);
        majorCSEntities.forEach(System.out::println);
    }
}
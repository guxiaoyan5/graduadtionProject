package edu.gyj.backend.mapper.major;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorTCSMapperTest {
    @Autowired
    MajorTCSMapper majorTCSMapper;

    @Test
    void findAll() {
        majorTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        majorTCSMapper.findByMajorId(1).forEach(System.out::println);
    }
}
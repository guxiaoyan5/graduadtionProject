package edu.gyj.backend.mapper.classCS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassTCSMapperTest {
    @Autowired
    ClassTCSMapper classTCSMapper;

    @Test
    void findAll() {
        classTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByClassId() {
        classTCSMapper.findByClassId(1).forEach(System.out::println);
    }
}
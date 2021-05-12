package edu.gyj.backend.mapper.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NewMajorMonthCSMapperTest {
    @Autowired
    NewMajorMonthCSMapper newClassMonthCSMapper;

    @Test
    void findAll() {
        newClassMonthCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByIdAndMonth() {
        newClassMonthCSMapper.findByIdAndMonth(1, 2021, 4).forEach(System.out::println);
    }
}
package edu.gyj.backend.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MealsTimeMapperTest {
    @Autowired
    MealsTimeMapper mealsTimeMapper;

    @Test
    void findAll() {
        mealsTimeMapper.findAll().forEach(System.out::println);
    }
}
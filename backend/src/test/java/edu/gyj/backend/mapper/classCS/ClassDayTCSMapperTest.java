package edu.gyj.backend.mapper.classCS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassDayTCSMapperTest {
    @Autowired
    ClassDayTCSMapper classDayTCSMapper;

    @Test
    void findAll() {
        classDayTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByClassId() {
        classDayTCSMapper.findByClassId(1).forEach(System.out::println);
    }

    @Test
    void findByDate() {
        classDayTCSMapper.findByDate(Date.valueOf("2021-4-1")).forEach(System.out::println);
    }

    @Test
    void findByDates() {
        classDayTCSMapper.findByDates(1, Date.valueOf("2021-4-1"), Date.valueOf("2021-4-2")).forEach(System.out::println);
    }
}
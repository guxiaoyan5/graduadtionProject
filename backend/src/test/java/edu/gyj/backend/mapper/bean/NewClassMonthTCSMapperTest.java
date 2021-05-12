package edu.gyj.backend.mapper.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.soap.SOAPBinding;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewClassMonthTCSMapperTest {
    @Autowired
    NewClassMonthTCSMapper newClassMonthTCSMapper;

    @Test
    void findAll() {
        newClassMonthTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByIdAndMonth() {
        newClassMonthTCSMapper.findByIdAndMonth(1,2021,4);
    }
}
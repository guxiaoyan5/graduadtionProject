package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClassDayCSMapperTest {
    @Autowired
    ClassDayCSMapper classDayCSMapper;
    @Test
    void findAll() {
        List<ClassDayCSEntity> classDayCSEntities = classDayCSMapper.findAll();
        classDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByClassId() {
        List<ClassDayCSEntity> classDayCSEntities = classDayCSMapper.findByClassId(1);
        classDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findDate() {
        List<ClassDayCSEntity> classDayCSEntities = classDayCSMapper.findByDate(Date.valueOf("2021-4-2"));
        classDayCSEntities.forEach(System.out::println);
    }
}
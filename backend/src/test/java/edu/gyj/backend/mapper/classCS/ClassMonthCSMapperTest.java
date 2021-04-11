package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClassMonthCSMapperTest {
    @Autowired
    ClassMonthCSMapper classMonthCSMapper;
    @Test
    void findAll() {
        List<ClassMonthCSEntity> classMonthCSEntities = classMonthCSMapper.findAll();
        classMonthCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByClassId() {
        List<ClassMonthCSEntity> classMonthCSEntities = classMonthCSMapper.findByClassId(1);
        classMonthCSEntities.forEach(e->System.out.println(e));
    }


    @Test
    void findByMonth() {
        List<ClassMonthCSEntity> classMonthCSEntities = classMonthCSMapper.findByMonth(3,2021);
        classMonthCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByYear() {
        List<ClassMonthCSEntity> classMonthCSEntities = classMonthCSMapper.findByYear(2021);
        classMonthCSEntities.forEach(e->System.out.println(e));
    }
}
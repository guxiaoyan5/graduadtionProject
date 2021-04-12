package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorMonthCSMapperTest {
    @Autowired
    MajorMonthCSMapper majorMonthCSMapper;

    @Test
    void findAll() {
        List<MajorMonthCSEntity> majorMonthCSEntities = majorMonthCSMapper.findAll();
        majorMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        List<MajorMonthCSEntity> majorMonthCSEntities = majorMonthCSMapper.findByMajorId(1);
        majorMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        List<MajorMonthCSEntity> majorMonthCSEntities = majorMonthCSMapper.findByMonth(4,2021);
        majorMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByYear() {
        List<MajorMonthCSEntity> majorMonthCSEntities = majorMonthCSMapper.findByYear(2021);
        majorMonthCSEntities.forEach(System.out::println);
    }
}
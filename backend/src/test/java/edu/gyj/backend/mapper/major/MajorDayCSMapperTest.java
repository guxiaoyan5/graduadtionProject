package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorDayCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorDayCSMapperTest {
    @Autowired
    MajorDayCSMapper majorDayCSMapper;

    @Test
    void findAll() {
        List<MajorDayCSEntity> majorDayCSEntities = majorDayCSMapper.findAll();
        majorDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        List<MajorDayCSEntity> majorDayCSEntities = majorDayCSMapper.findByMajorId(1);
        majorDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByDate() {
        List<MajorDayCSEntity> majorDayCSEntities = majorDayCSMapper.findByDate(Date.valueOf("2021-4-1"));
        majorDayCSEntities.forEach(System.out::println);
    }
}
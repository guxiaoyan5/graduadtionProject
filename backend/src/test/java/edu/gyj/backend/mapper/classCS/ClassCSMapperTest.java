package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.ws.Action;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClassCSMapperTest {
    @Autowired
    ClassCSMapper classCSMapper;
    @Test
    void findAll() {
        List<ClassCSEntity> classCSEntities = classCSMapper.findAll();
        classCSEntities.forEach(System.out::println);
    }

    @Test
    void findByClassId() {
        List<ClassCSEntity> classCSEntities = classCSMapper.findByClassId(1);
        classCSEntities.forEach(System.out::println);
    }
}
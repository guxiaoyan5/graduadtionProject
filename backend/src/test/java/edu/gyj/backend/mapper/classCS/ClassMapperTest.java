package edu.gyj.backend.mapper.classCS;

import edu.gyj.backend.entity.classCS.ClassEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassMapperTest {
    @Autowired
    ClassMapper classMapper;

    @Test
    void findAll() {
        List<ClassEntity> classEntities = classMapper.findAll();
        classEntities.forEach(System.out::println);
    }

    @Test
    void findById() {
        ClassEntity classEntity = classMapper.findById(1);
        System.out.println(classEntity);
    }

    @Test
    void findByMajorId() {
        List<ClassEntity> classEntity = classMapper.findByMajorId(1);
        classEntity.forEach(System.out::println);
    }

    @Test
    void insertClass() {
        classMapper.insertClass(new ClassEntity(-1, "zxcv", 1));
    }

    @Test
    void updateClass() {
        classMapper.updateClass(new ClassEntity(-1, "", 2));
    }

    @Test
    void deleteClass() {
        classMapper.deleteClass(9);
    }
}
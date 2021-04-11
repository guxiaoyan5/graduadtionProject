package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.SchoolUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class SchoolUserMapperTest{
    @Autowired
    SchoolUserMapper schoolUserMapper;
    @Test
    void findByName() {
        System.out.println(schoolUserMapper.findByName("1234567890"));
    }

    @Test
    void findAll() {
        List<SchoolUserEntity> schoolUserEntities = schoolUserMapper.findAll();
        schoolUserEntities.forEach(System.out::println);
    }

    @Test
    void insertSchoolUser() {
        schoolUserMapper.insertSchoolUser(new SchoolUserEntity("1234567890","1234657890","1234567890"));
        schoolUserMapper.insertSchoolUser(new SchoolUserEntity("1234567891","1234657891","1234567891"));
        schoolUserMapper.insertSchoolUser(new SchoolUserEntity("1234567892","1234657892","1234567892"));
    }

    @Test
    void updateSchoolUser() {
        schoolUserMapper.updateSchoolUser(new SchoolUserEntity("1234567891",null,"253"));
        schoolUserMapper.updateSchoolUser(new SchoolUserEntity("1234567890","123",null));
    }

    @Test
    void deleteSchoolUser() {
        schoolUserMapper.deleteSchoolUser("1234567890");
    }
}
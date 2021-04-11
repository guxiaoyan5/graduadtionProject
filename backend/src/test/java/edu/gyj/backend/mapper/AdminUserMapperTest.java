package edu.gyj.backend.mapper;

import edu.gyj.backend.entity.AdminUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminUserMapperTest {
    @Autowired
    AdminUserMapper adminUserMapper;

    @Test
    void findById() {
        AdminUserEntity adminUserEntity = adminUserMapper.findById("root");
        System.out.println(adminUserEntity);
    }

    @Test
    void updatePassword() {
       adminUserMapper.updatePassword(new AdminUserEntity("root","root"));
    }
}
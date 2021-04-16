package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.AdminUserEntity;
import edu.gyj.backend.mapper.AdminUserMapper;
import edu.gyj.backend.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper adminUserMapper;
    @Override
    public int updatePassword(AdminUserEntity adminUserEntity) {
        return adminUserMapper.updatePassword(adminUserEntity);
    }

    @Override
    public AdminUserEntity login(String id) {
        return adminUserMapper.findById(id);
    }
}

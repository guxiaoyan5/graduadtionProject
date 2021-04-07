package edu.gyj.graduate.service.impl;

import edu.gyj.graduate.entity.AdminUser;
import edu.gyj.graduate.mapper.AdminUserMapper;
import edu.gyj.graduate.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser findByName(String name) {
        return adminUserMapper.findByName(name);
    }
}

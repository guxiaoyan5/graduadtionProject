package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.AdminUserEntity;
import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.mapper.AdminUserMapper;
import edu.gyj.backend.mapper.SchoolUserMapper;
import edu.gyj.backend.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    AdminUserMapper adminUserMapper;
    @Autowired
    SchoolUserMapper schoolUserMapper;

    @Override
    public int updatePassword(AdminUserEntity adminUserEntity) {
        return adminUserMapper.updatePassword(adminUserEntity);
    }

    @Override
    public AdminUserEntity login(String id) {
        return adminUserMapper.findById(id);
    }

    @Override
    public List<SchoolUserEntity> getSchoolUserAll() {
        return schoolUserMapper.findAll();
    }

    @Override
    public int updateSchoolPassword(SchoolUserEntity schoolUserEntity) {
        int result = schoolUserMapper.updateSchoolUser(schoolUserEntity);
        return result;
    }

    @Override
    public int updateSchoolName(SchoolUserEntity schoolUserEntity) {
        int result = schoolUserMapper.updateSchoolUser(schoolUserEntity);
        return result;
    }

    @Override
    public int deleteSchoolUser(String id) {
        int result = schoolUserMapper.deleteSchoolUser(id);
        return result;
    }

    @Override
    public int addSchoolUser(SchoolUserEntity schoolUserEntity) {
        return schoolUserMapper.insertSchoolUser(schoolUserEntity);
    }
}

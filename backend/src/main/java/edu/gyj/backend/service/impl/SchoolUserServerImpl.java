package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.mapper.SchoolUserMapper;
import edu.gyj.backend.service.SchoolUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolUserServerImpl implements SchoolUserService {
    @Autowired
    SchoolUserMapper schoolUserMapper;

    @Override
    public SchoolUserEntity login(String id) {
        SchoolUserEntity s = schoolUserMapper.findById(id);
        return s;
    }

    @Override
    public int updatePassword(SchoolUserEntity schoolUserEntity) {
        int result = schoolUserMapper.updateSchoolUser(schoolUserEntity);
        return result;
    }

    @Override
    public int updateName(SchoolUserEntity schoolUserEntity) {
        return schoolUserMapper.updateSchoolUser(schoolUserEntity);
    }
}

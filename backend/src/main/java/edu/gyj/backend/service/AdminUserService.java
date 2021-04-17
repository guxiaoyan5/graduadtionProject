package edu.gyj.backend.service;

import edu.gyj.backend.entity.AdminUserEntity;
import edu.gyj.backend.entity.SchoolUserEntity;

import java.util.List;

public interface AdminUserService {
    public int updatePassword(AdminUserEntity adminUserEntity);
    public AdminUserEntity login(String id);
    public List<SchoolUserEntity> getSchoolUserAll();
    public int updateSchoolPassword(SchoolUserEntity schoolUserEntity);
    public int updateSchoolName(SchoolUserEntity schoolUserEntity);
    public int deleteSchoolUser(String id);
    public int addSchoolUser(SchoolUserEntity schoolUserEntity);
}

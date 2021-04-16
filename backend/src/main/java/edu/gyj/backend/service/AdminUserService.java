package edu.gyj.backend.service;

import edu.gyj.backend.entity.AdminUserEntity;

public interface AdminUserService {
    public int updatePassword(AdminUserEntity adminUserEntity);
    public AdminUserEntity login(String id);
}

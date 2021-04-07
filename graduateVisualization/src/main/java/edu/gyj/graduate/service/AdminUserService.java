package edu.gyj.graduate.service;

import edu.gyj.graduate.entity.AdminUser;

public interface AdminUserService {
    AdminUser findByName(String name);
}

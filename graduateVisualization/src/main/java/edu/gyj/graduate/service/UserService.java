package edu.gyj.graduate.service;

import edu.gyj.graduate.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    List<User> findAll();
}

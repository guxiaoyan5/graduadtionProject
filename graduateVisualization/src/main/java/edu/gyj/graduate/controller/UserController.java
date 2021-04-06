package edu.gyj.graduate.controller;

import edu.gyj.graduate.entity.User;
import edu.gyj.graduate.mapper.UserMapper;
import edu.gyj.graduate.service.UserService;
import edu.gyj.graduate.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private User findUser(@RequestParam(value = "id") Long id) {
        return userService.findById(id);
    }
    @RequestMapping("/user")
    private List<User> findAllUser(){
        return userService.findAll();
    }
}

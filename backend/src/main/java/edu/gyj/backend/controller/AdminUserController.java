package edu.gyj.backend.controller;

import edu.gyj.backend.entity.AdminUserEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.AdminUserService;
import edu.gyj.backend.service.impl.AdminUserServiceImpl;
import edu.gyj.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/adminUser")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody AdminUserEntity adminUserEntity) {
        AdminUserEntity adminUserEntity1 = adminUserService.login(adminUserEntity.getId());
        if (adminUserEntity1 == null) {
            return new Result(0, "账号不存在");
        } else if (!adminUserEntity1.getPassword().equals(adminUserEntity.getPassword())) {
            return new Result(1, "密码错误");
        } else if (adminUserEntity1.getPassword().equals(adminUserEntity.getPassword())) {
            adminUserEntity1.setPassword(null);
            String token = TokenUtil.sign(adminUserEntity1);
            return new Result(2, "登录成功", token, adminUserEntity1);
        } else {
            return new Result(3, "未知错误");
        }
    }
}

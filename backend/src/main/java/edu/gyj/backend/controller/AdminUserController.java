package edu.gyj.backend.controller;

import edu.gyj.backend.entity.AdminUserEntity;
import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.result.IdResult;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.AdminUserService;
import edu.gyj.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseBody
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public Result updatePassword(@RequestBody AdminUserEntity adminUserEntity){
        int result = adminUserService.updatePassword(adminUserEntity);
        if(result == 1){
            return new Result(1,"修改成功");
        }else{
            return new Result(0,"修改失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/schoolUserAll",method = RequestMethod.GET)
    public Result getAllSchoolUser(){
        List<SchoolUserEntity> schoolUserEntities = adminUserService.getSchoolUserAll();
        if(schoolUserEntities!=null){
            return new Result(1,"读取成功",schoolUserEntities);
        }
        return new Result(0,"读取失败");
    }
    @ResponseBody
    @RequestMapping(value = "/updateSchoolPassword",method = RequestMethod.POST)
    public Result updateSchoolPassword(@RequestBody SchoolUserEntity schoolUserEntity){
        schoolUserEntity.setName(null);
        int result = adminUserService.updateSchoolPassword(schoolUserEntity);
        if (result == 1) {
            return new Result(1, "修改成功");
        }
        return new Result(0, "修改失败");
    }

    @ResponseBody
    @RequestMapping(value = "/updateStudentName", method = RequestMethod.POST)
    public Result updateSchoolName(@RequestBody SchoolUserEntity schoolUserEntity) {
        int result = adminUserService.updateSchoolName(schoolUserEntity);
        if (result == 1) {
            return new Result(1, "修改昵称成功",schoolUserEntity);
        }
        return new Result(0, "修改失败");
    }
    @ResponseBody
    @RequestMapping(value = "/deleteSchoolUser",method = RequestMethod.POST)
    public Result deleteSchoolName(@RequestBody IdResult id){
        int result = adminUserService.deleteSchoolUser(id.getId());
        if (result == 1) {
            return new Result(1, "删除成功");
        }
        return new Result(0, "删除失败");
    }

    @ResponseBody
    @RequestMapping(value = "/addSchoolUser", method = RequestMethod.POST)
    public Result addSchoolUser(@RequestBody SchoolUserEntity schoolUserEntity){
        int result = adminUserService.addSchoolUser(schoolUserEntity);
        if(result == 1){
            return new Result(1,"添加成功");
        }else{
            return new Result(2,"添加失败");
        }
    }

}

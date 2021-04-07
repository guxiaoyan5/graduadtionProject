package edu.gyj.graduate.controller;

import edu.gyj.graduate.entity.AdminUser;
import edu.gyj.graduate.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("adminLogin");
    }

    @RequestMapping("/successLogin")
    public ModelAndView successLogin(){
        return new ModelAndView("successLogin");
    }
    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(@RequestBody AdminUser user) {
        AdminUser result = adminUserService.findByName(user.getName());
        if (result == null) {
            return "0";
        }
        return "1";
    }


    @ResponseBody
    @RequestMapping(value = "/selectUserName", method = RequestMethod.POST)
    public String selectUserName(@RequestBody AdminUser user) {
        String userName = user.getName();
        String userPassword = user.getPassword();
        String result = "-1";

        //用户不存在
        if (adminUserService.findByName(userName) == null) {
            result = "0";
            return result;
        }else
        if(!adminUserService.findByName(userName).getPassword().equals(userPassword) ){
            result = "1";
            return result;

        }else if(adminUserService.findByName(userName).getPassword().equals(userPassword)) {
            result = "2";
            return result;
        }
        return result;
    }



}

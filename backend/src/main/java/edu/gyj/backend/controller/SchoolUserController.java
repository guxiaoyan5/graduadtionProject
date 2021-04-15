package edu.gyj.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.result.ResultSchoolUserLogin;
import edu.gyj.backend.service.SchoolUserService;
import edu.gyj.backend.service.impl.SchoolUserServerImpl;
import edu.gyj.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/schoolUser")
public class SchoolUserController {
    @Autowired
    SchoolUserService schoolUserServer;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultSchoolUserLogin login(@RequestBody SchoolUserEntity schoolUserEntity) {
        SchoolUserEntity s = schoolUserServer.login(schoolUserEntity.getId());
        if (s == null) {
            return new ResultSchoolUserLogin(0, "不存在该账号");
        } else if (!s.getPassword().equals(schoolUserEntity.getPassword())) {
            return new ResultSchoolUserLogin(1, "密码错误");
        } else if (s.getPassword().equals(schoolUserEntity.getPassword())) {
            String token = TokenUtil.sign(s);
            System.out.println(token);
            return new ResultSchoolUserLogin(2, "正确",token);
        } else {
            return new ResultSchoolUserLogin(3, "未知错误");
        }
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test() throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("data","data");
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}

package edu.gyj.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.SchoolUserService;
import edu.gyj.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/schoolUser")
public class SchoolUserController {
    @Autowired
    SchoolUserService schoolUserServer;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody SchoolUserEntity schoolUserEntity) {
        SchoolUserEntity s = schoolUserServer.login(schoolUserEntity.getId());
        if (s == null) {
            return new Result(0, "不存在该账号");
        } else if (!s.getPassword().equals(schoolUserEntity.getPassword())) {
            return new Result(1, "密码错误");
        } else if (s.getPassword().equals(schoolUserEntity.getPassword())) {
            s.setPassword("");
            String token = TokenUtil.sign(s);
            return new Result(2, "正确", token, s);
        } else {
            return new Result(3, "未知错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Result updatePassword(@RequestBody SchoolUserEntity schoolUserEntity) {
        int result = schoolUserServer.updatePassword(schoolUserEntity);
        if (result == 1) {
            return new Result(1, "修改成功");
        }
        return new Result(0, "修改失败");
    }

    @ResponseBody
    @RequestMapping(value = "updateName", method = RequestMethod.POST)
    public Result updateName(@RequestBody SchoolUserEntity schoolUserEntity) {
        int result = schoolUserServer.updateName(schoolUserEntity);
        if (result == 1) {
            System.out.println(schoolUserEntity);
            return new Result(1, "修改昵称成功",schoolUserEntity);
        }
        return new Result(0, "修改失败");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() throws JsonProcessingException {
        HashMap<String, Object> hs = new HashMap<>();
        hs.put("data", "data");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}

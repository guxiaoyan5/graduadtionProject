package edu.gyj.backend.controller;

import edu.gyj.backend.result.Result;
import edu.gyj.backend.result.StudentResult;
import edu.gyj.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public Result getAll(){
        List<StudentResult> studentResultList = studentService.getAll();
        if(studentResultList != null){
            return new Result(1,"读取成功",studentResultList);
        }else{
            return new Result(0,"读取失败");
        }
    }
}

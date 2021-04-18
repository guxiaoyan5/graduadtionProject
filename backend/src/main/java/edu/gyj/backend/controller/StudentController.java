package edu.gyj.backend.controller;

import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.entity.student.StudentEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.result.StudentResult;
import edu.gyj.backend.service.StudentService;
import org.apache.ibatis.javassist.ClassMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result getAll() {
        List<StudentResult> studentResultList = studentService.getAll();
        if (studentResultList != null) {
            return new Result(1, "读取成功", studentResultList);
        } else {
            return new Result(0, "读取失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getByClassId", method = RequestMethod.POST)
    public Result getByClassId(@RequestBody ClassEntity classEntity) {
        List<StudentResult> studentResults = studentService.getByClassId(classEntity);
        if (studentResults != null) {
            return new Result(1, "查询成功", studentResults);
        } else {
            return new Result(0, "查询失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody StudentEntity studentEntity) {
        int result = studentService.add(studentEntity);
        if (result == 1) {
            return new Result(1, "添加成功");
        } else {
            return new Result(0, "添加失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody StudentEntity studentEntity){
        int result = studentService.update(studentEntity);
        if (result == 1) {
            return new Result(1, "修改成功");
        } else {
            return new Result(0, "修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody StudentEntity studentEntity){
        int result = studentService.delete(studentEntity);
        if (result == 1) {
            return new Result(1, "删除成功");
        } else {
            return new Result(0, "删除失败");
        }
    }
}

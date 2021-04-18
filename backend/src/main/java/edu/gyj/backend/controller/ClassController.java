package edu.gyj.backend.controller;

import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.result.ClassResult;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/class")
public class ClassController {
    @Autowired
    ClassService classService;

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result findAll() {
        List<ClassResult> classResults = classService.getAll();
        if (classResults != null) {
            return new Result(1, "读取成功", classResults);
        } else {
            return new Result(0, "读取失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody ClassEntity classEntity) {
        int result = classService.add(classEntity);
        if (result == 1) {
            return new Result(1, "添加成功");
        } else if (result == -1) {
            return new Result(2, "已存在该班级");
        } else {
            return new Result(0, "添加失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody ClassEntity classEntity) {
        int result = classService.update(classEntity);
        if (result == 1) {
            return new Result(1, "修改成功");
        } else {
            return new Result(0, "修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody ClassEntity classEntity){
        int result = classService.delete(classEntity);
        if (result == 1) {
            return new Result(1, "删除成功");
        } else {
            return new Result(0, "删除失败");
        }
    }
}

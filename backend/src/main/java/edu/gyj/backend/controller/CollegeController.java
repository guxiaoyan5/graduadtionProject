package edu.gyj.backend.controller;

import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/college")
public class CollegeController {
    @Autowired
    CollegeService collegeService;

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result getAll() {
        List<CollegeEntity> collegeEntities = collegeService.findAll();
        if (collegeEntities != null) {
            return new Result(1, "加载成功", collegeEntities);
        } else {
            return new Result(0, "加载失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody CollegeEntity collegeEntity){
        System.out.println();
        int result = collegeService.add(collegeEntity);
        if(result == -1){
            return new Result(2,"已存在该学院名");
        }else if(result == 1){
            return new Result(1,"添加成功");
        }else{
            return new Result(0,"添加失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody CollegeEntity collegeEntity){
        int result = collegeService.update(collegeEntity);
        if(result == 1){
            return new Result(1,"修改成功");
        }else{
            return new Result(0,"修改失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody CollegeEntity collegeEntity){
        int result = collegeService.delete(collegeEntity);
        if(result == 1){
            return new Result(1,"删除成功");
        }else{
            return new Result(0,"删除失败");
        }
    }
}

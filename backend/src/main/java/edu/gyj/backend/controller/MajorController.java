package edu.gyj.backend.controller;

import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.result.MajorResult;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/major")
public class MajorController {
    @Autowired
    MajorService majorService;
    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public Result getAll(){
        List<MajorResult> majorResults = majorService.getAll();
        if(majorResults!=null){
            return new Result(1,"读取成功",majorResults);
        }else {
            return new Result(0,"读取失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody MajorEntity majorEntity){
        int result = majorService.addMajor(majorEntity);
        if(result == 1){
            return new Result(1,"添加成功");
        }else{
            return new Result(0,"添加失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody MajorEntity majorEntity){
        System.out.println(majorEntity);
        int result = majorService.update(majorEntity);
        if(result == 1){
            return new Result(1,"修改成功");
        }else{
            return new Result(0,"修改失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody MajorEntity majorEntity){
        int result = majorService.delete(majorEntity);
        if(result == 1){
            return new Result(1,"删除成功");
        }else{
            return new Result(0,"删除失败");
        }
    }
}

package edu.gyj.backend.controller;

import edu.gyj.backend.entity.store.StoreEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
public class StoreController {
    @Autowired
    StoreService storeService;
    @ResponseBody
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public Result getAll(){
        List<StoreEntity> storeEntities = storeService.getAll();
        if(storeEntities!=null){
            return new Result(1,"读取成功",storeEntities);
        }else{
            return new Result(0,"读取失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody StoreEntity storeEntity){
        int result = storeService.add(storeEntity);
        if(result==1){
            return new Result(1,"添加成功");
        }else{
            return new Result(0,"添加失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody StoreEntity storeEntity){
        int result = storeService.update(storeEntity);
        if(result==1){
            return new Result(1,"修改成功");
        }else{
            return new Result(0,"修改失败");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody StoreEntity storeEntity){
        int result = storeService.delete(storeEntity);
        if(result==1){
            return new Result(1,"删除成功");
        }else{
            return new Result(0,"删除失败");
        }
    }
}

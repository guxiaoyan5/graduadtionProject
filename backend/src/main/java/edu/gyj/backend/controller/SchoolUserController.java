package edu.gyj.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gyj.backend.Input.DataInput;
import edu.gyj.backend.Input.Node;
import edu.gyj.backend.Input.QueryDataInput;
import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.entity.major.MajorDayCSEntity;
import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import edu.gyj.backend.result.*;
import edu.gyj.backend.service.ClassService;
import edu.gyj.backend.service.CollegeService;
import edu.gyj.backend.service.MajorService;
import edu.gyj.backend.service.SchoolUserService;
import edu.gyj.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/schoolUser")
public class SchoolUserController {
    @Autowired
    SchoolUserService schoolUserServer;
    @Autowired
    CollegeService collegeService;
    @Autowired
    MajorService majorService;
    @Autowired
    ClassService classService;

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
            return new Result(1, "修改昵称成功", schoolUserEntity);
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

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Result getData(@RequestBody DataInput dataInput) {
        List<CollegeEntity> collegeEntities;
        List<MajorEntity> majorEntities;
        List<ClassEntity> classEntities;
        List<DataResult> dataResults = new ArrayList<>();
        if (dataInput.getLevel() == 0) {
            collegeEntities = collegeService.findAll();
            for (CollegeEntity collegeEntity : collegeEntities) {
                dataResults.add(new DataResult(collegeEntity.getId(), collegeEntity.getCollege(), false));
            }
        } else if (dataInput.getLevel() == 1) {
            majorEntities = majorService.getCollegeId(dataInput.getValue());
            for (MajorEntity majorEntity : majorEntities) {
                dataResults.add(new DataResult(majorEntity.getId(), majorEntity.getMajor(), false));
            }
        } else if (dataInput.getLevel() == 2) {
            classEntities = classService.getMajorId(dataInput.getValue());
            for (ClassEntity classEntity : classEntities) {
                dataResults.add(new DataResult(classEntity.getId(), classEntity.getName(), true));
            }
        } else {
            return new Result(0, "加载失败");
        }
        return new Result(1, "加载成功", dataResults);
    }

    @ResponseBody
    @RequestMapping(value = "/getConsume", method = RequestMethod.POST)
    public Result getConsume(@RequestBody QueryDataInput queryDataInput) {
        Calendar calendar = Calendar.getInstance();
        if (queryDataInput.isChoice()) {
            calendar.setTime(queryDataInput.getDate().get(0));
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH) + 1;
            calendar.setTime(queryDataInput.getDate().get(1));
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH) + 1;
            List<ConsumeMonthResult> consumeMonthResults = new ArrayList<>();
            for (Node node : queryDataInput.getId()) {
                ConsumeMonthResult consumeMonthResult = new ConsumeMonthResult(node.getValue(), node.getLabel());
                if (node.getLevel() == 1) {
                    if (startYear != endYear) {
                        for (int i = startMonth; i <= 12; i++) {
                            List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeService.findByCollegeIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            collegeMonthCSEntities.forEach(collegeMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    collegeMonthCSEntity.getMonth(),
                                    collegeMonthCSEntity.getYear(),
                                    collegeMonthCSEntity.getConsumption_count(),
                                    collegeMonthCSEntity.getConsumption_total_money(),
                                    collegeMonthCSEntity.getConsumption_average_money(),
                                    collegeMonthCSEntity.getConsumption_student_average_money(),
                                    collegeMonthCSEntity.getStudent_count(),
                                    collegeMonthCSEntity.getConsumption_low_count(),
                                    collegeMonthCSEntity.getConsumption_high_count(),
                                    collegeMonthCSEntity.getStudent_low_count(),
                                    collegeMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = startYear + 1; i < endYear; i++) {
                            List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeService.findByCollegeIdAndYear(node.getValue(), i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            collegeMonthCSEntities.forEach(collegeMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    collegeMonthCSEntity.getMonth(),
                                    collegeMonthCSEntity.getYear(),
                                    collegeMonthCSEntity.getConsumption_count(),
                                    collegeMonthCSEntity.getConsumption_total_money(),
                                    collegeMonthCSEntity.getConsumption_average_money(),
                                    collegeMonthCSEntity.getConsumption_student_average_money(),
                                    collegeMonthCSEntity.getStudent_count(),
                                    collegeMonthCSEntity.getConsumption_low_count(),
                                    collegeMonthCSEntity.getConsumption_high_count(),
                                    collegeMonthCSEntity.getStudent_low_count(),
                                    collegeMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = endMonth; i > 0; i--) {
                            List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeService.findByCollegeIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            collegeMonthCSEntities.forEach(collegeMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    collegeMonthCSEntity.getMonth(),
                                    collegeMonthCSEntity.getYear(),
                                    collegeMonthCSEntity.getConsumption_count(),
                                    collegeMonthCSEntity.getConsumption_total_money(),
                                    collegeMonthCSEntity.getConsumption_average_money(),
                                    collegeMonthCSEntity.getConsumption_student_average_money(),
                                    collegeMonthCSEntity.getStudent_count(),
                                    collegeMonthCSEntity.getConsumption_low_count(),
                                    collegeMonthCSEntity.getConsumption_high_count(),
                                    collegeMonthCSEntity.getStudent_low_count(),
                                    collegeMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    } else {
                        for (int i = startMonth; i <= endMonth; i++) {
                            List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeService.findByCollegeIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            collegeMonthCSEntities.forEach(collegeMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    collegeMonthCSEntity.getMonth(),
                                    collegeMonthCSEntity.getYear(),
                                    collegeMonthCSEntity.getConsumption_count(),
                                    collegeMonthCSEntity.getConsumption_total_money(),
                                    collegeMonthCSEntity.getConsumption_average_money(),
                                    collegeMonthCSEntity.getConsumption_student_average_money(),
                                    collegeMonthCSEntity.getStudent_count(),
                                    collegeMonthCSEntity.getConsumption_low_count(),
                                    collegeMonthCSEntity.getConsumption_high_count(),
                                    collegeMonthCSEntity.getStudent_low_count(),
                                    collegeMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    }
                } else if (node.getLevel() == 2) {
                    if (startYear != endYear) {
                        for (int i = startMonth; i <= 12; i++) {
                            List<MajorMonthCSEntity> majorMonthCSEntities = majorService.findByMajorIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            majorMonthCSEntities.forEach(majorMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    majorMonthCSEntity.getMonth(),
                                    majorMonthCSEntity.getYear(),
                                    majorMonthCSEntity.getConsumption_count(),
                                    majorMonthCSEntity.getConsumption_total_money(),
                                    majorMonthCSEntity.getConsumption_average_money(),
                                    majorMonthCSEntity.getConsumption_student_average_money(),
                                    majorMonthCSEntity.getStudent_count(),
                                    majorMonthCSEntity.getConsumption_low_count(),
                                    majorMonthCSEntity.getConsumption_high_count(),
                                    majorMonthCSEntity.getStudent_low_count(),
                                    majorMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = startYear + 1; i < endYear; i++) {
                            List<MajorMonthCSEntity> majorMonthCSEntities = majorService.findByMajorIdAndYear(node.getValue(), i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            majorMonthCSEntities.forEach(majorMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    majorMonthCSEntity.getMonth(),
                                    majorMonthCSEntity.getYear(),
                                    majorMonthCSEntity.getConsumption_count(),
                                    majorMonthCSEntity.getConsumption_total_money(),
                                    majorMonthCSEntity.getConsumption_average_money(),
                                    majorMonthCSEntity.getConsumption_student_average_money(),
                                    majorMonthCSEntity.getStudent_count(),
                                    majorMonthCSEntity.getConsumption_low_count(),
                                    majorMonthCSEntity.getConsumption_high_count(),
                                    majorMonthCSEntity.getStudent_low_count(),
                                    majorMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = endMonth; i > 0; i--) {
                            List<MajorMonthCSEntity> majorMonthCSEntities = majorService.findByMajorIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            majorMonthCSEntities.forEach(majorMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    majorMonthCSEntity.getMonth(),
                                    majorMonthCSEntity.getYear(),
                                    majorMonthCSEntity.getConsumption_count(),
                                    majorMonthCSEntity.getConsumption_total_money(),
                                    majorMonthCSEntity.getConsumption_average_money(),
                                    majorMonthCSEntity.getConsumption_student_average_money(),
                                    majorMonthCSEntity.getStudent_count(),
                                    majorMonthCSEntity.getConsumption_low_count(),
                                    majorMonthCSEntity.getConsumption_high_count(),
                                    majorMonthCSEntity.getStudent_low_count(),
                                    majorMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    } else {
                        for (int i = startMonth; i <= endMonth; i++) {
                            List<MajorMonthCSEntity> majorMonthCSEntities = majorService.findByMajorIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            majorMonthCSEntities.forEach(majorMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    majorMonthCSEntity.getMonth(),
                                    majorMonthCSEntity.getYear(),
                                    majorMonthCSEntity.getConsumption_count(),
                                    majorMonthCSEntity.getConsumption_total_money(),
                                    majorMonthCSEntity.getConsumption_average_money(),
                                    majorMonthCSEntity.getConsumption_student_average_money(),
                                    majorMonthCSEntity.getStudent_count(),
                                    majorMonthCSEntity.getConsumption_low_count(),
                                    majorMonthCSEntity.getConsumption_high_count(),
                                    majorMonthCSEntity.getStudent_low_count(),
                                    majorMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    }
                } else if (node.getLevel() == 3) {
                    if (startYear != endYear) {
                        for (int i = startMonth; i <= 12; i++) {
                            List<ClassMonthCSEntity> classMonthCSEntities = classService.findByClassIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            classMonthCSEntities.forEach(classMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    classMonthCSEntity.getMonth(),
                                    classMonthCSEntity.getYear(),
                                    classMonthCSEntity.getConsumption_count(),
                                    classMonthCSEntity.getConsumption_total_money(),
                                    classMonthCSEntity.getConsumption_average_money(),
                                    classMonthCSEntity.getConsumption_student_average_money(),
                                    classMonthCSEntity.getStudent_count(),
                                    classMonthCSEntity.getConsumption_low_count(),
                                    classMonthCSEntity.getConsumption_high_count(),
                                    classMonthCSEntity.getStudent_low_count(),
                                    classMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = startYear + 1; i < endYear; i++) {
                            List<ClassMonthCSEntity> classMonthCSEntities = classService.findByClassIdAndYear(node.getValue(), i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            classMonthCSEntities.forEach(classMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    classMonthCSEntity.getMonth(),
                                    classMonthCSEntity.getYear(),
                                    classMonthCSEntity.getConsumption_count(),
                                    classMonthCSEntity.getConsumption_total_money(),
                                    classMonthCSEntity.getConsumption_average_money(),
                                    classMonthCSEntity.getConsumption_student_average_money(),
                                    classMonthCSEntity.getStudent_count(),
                                    classMonthCSEntity.getConsumption_low_count(),
                                    classMonthCSEntity.getConsumption_high_count(),
                                    classMonthCSEntity.getStudent_low_count(),
                                    classMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                        for (int i = endMonth; i > 0; i--) {
                            List<ClassMonthCSEntity> classMonthCSEntities = classService.findByClassIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            classMonthCSEntities.forEach(classMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    classMonthCSEntity.getMonth(),
                                    classMonthCSEntity.getYear(),
                                    classMonthCSEntity.getConsumption_count(),
                                    classMonthCSEntity.getConsumption_total_money(),
                                    classMonthCSEntity.getConsumption_average_money(),
                                    classMonthCSEntity.getConsumption_student_average_money(),
                                    classMonthCSEntity.getStudent_count(),
                                    classMonthCSEntity.getConsumption_low_count(),
                                    classMonthCSEntity.getConsumption_high_count(),
                                    classMonthCSEntity.getStudent_low_count(),
                                    classMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    } else {
                        for (int i = startMonth; i <= endMonth; i++) {
                            List<ClassMonthCSEntity> classMonthCSEntities = classService.findByClassIdAndYearAndMonth(node.getValue(), startYear, i);
                            List<ConsumeMonthData> consumeMonthData = new ArrayList<>();
                            classMonthCSEntities.forEach(classMonthCSEntity -> consumeMonthData.add(new ConsumeMonthData(
                                    classMonthCSEntity.getMonth(),
                                    classMonthCSEntity.getYear(),
                                    classMonthCSEntity.getConsumption_count(),
                                    classMonthCSEntity.getConsumption_total_money(),
                                    classMonthCSEntity.getConsumption_average_money(),
                                    classMonthCSEntity.getConsumption_student_average_money(),
                                    classMonthCSEntity.getStudent_count(),
                                    classMonthCSEntity.getConsumption_low_count(),
                                    classMonthCSEntity.getConsumption_high_count(),
                                    classMonthCSEntity.getStudent_low_count(),
                                    classMonthCSEntity.getStudent_low_count()
                            )));
                            consumeMonthResult.getConsumeMonthData().addAll(consumeMonthData);
                        }
                    }
                }
                consumeMonthResults.add(consumeMonthResult);
            }
            return new Result(1, "加载成功", consumeMonthResults);
        } else {
            List<ConsumeDayResult> consumeDayResults = new ArrayList<>();
            Date startDate = queryDataInput.getDate().get(0);
            Date endDate = queryDataInput.getDate().get(1);
            for(Node node:queryDataInput.getId()){
                ConsumeDayResult consumeDayResult = new ConsumeDayResult(node.getValue(),node.getLabel());
                if(node.getLevel()==1){
                    List<CollegeDayCSEntity> collegeDayCSEntities = collegeService.findByCollegeIdAndDates(node.getValue(),
                            startDate,endDate);
                    List<ConsumeDayData> consumeDayData = new ArrayList<>();
                    collegeDayCSEntities.forEach(collegeDayCSEntity -> consumeDayData.add(new ConsumeDayData(
                            collegeDayCSEntity.getDay(),
                            collegeDayCSEntity.getConsumption_count(),
                            collegeDayCSEntity.getConsumption_total_money(),
                            collegeDayCSEntity.getConsumption_average_money(),
                            collegeDayCSEntity.getConsumption_student_average_money(),
                            collegeDayCSEntity.getStudent_count(),
                            collegeDayCSEntity.getConsumption_low_count(),
                            collegeDayCSEntity.getConsumption_high_count(),
                            collegeDayCSEntity.getStudent_low_count(),
                            collegeDayCSEntity.getStudent_low_count()
                    )));
                    consumeDayResult.getConsumeDayData().addAll(consumeDayData);
                }else if(node.getLevel() == 2){
                    List<MajorDayCSEntity> majorDayCSEntities = majorService.findByMajorIdAndDates(node.getValue(),
                            startDate,endDate);
                    List<ConsumeDayData> consumeDayData = new ArrayList<>();
                    majorDayCSEntities.forEach(classDayCSEntity -> consumeDayData.add(new ConsumeDayData(
                            classDayCSEntity.getDay(),
                            classDayCSEntity.getConsumption_count(),
                            classDayCSEntity.getConsumption_total_money(),
                            classDayCSEntity.getConsumption_average_money(),
                            classDayCSEntity.getConsumption_student_average_money(),
                            classDayCSEntity.getStudent_count(),
                            classDayCSEntity.getConsumption_low_count(),
                            classDayCSEntity.getConsumption_high_count(),
                            classDayCSEntity.getStudent_low_count(),
                            classDayCSEntity.getStudent_low_count()
                    )));
                    consumeDayResult.getConsumeDayData().addAll(consumeDayData);
                }else if(node.getLevel() == 3){
                    List<ClassDayCSEntity> classDayCSEntities = classService.findByClassIdAndDates(node.getValue(),
                            startDate,endDate);
                    List<ConsumeDayData> consumeDayData = new ArrayList<>();
                    classDayCSEntities.forEach(classDayCSEntity -> consumeDayData.add(new ConsumeDayData(
                            classDayCSEntity.getDay(),
                            classDayCSEntity.getConsumption_count(),
                            classDayCSEntity.getConsumption_total_money(),
                            classDayCSEntity.getConsumption_average_money(),
                            classDayCSEntity.getConsumption_student_average_money(),
                            classDayCSEntity.getStudent_count(),
                            classDayCSEntity.getConsumption_low_count(),
                            classDayCSEntity.getConsumption_high_count(),
                            classDayCSEntity.getStudent_low_count(),
                            classDayCSEntity.getStudent_low_count()
                    )));
                    consumeDayResult.getConsumeDayData().addAll(consumeDayData);
                }
                consumeDayResults.add(consumeDayResult);
            }
            return new Result(1,"加载成功",consumeDayResults);
        }
    }
}

package edu.gyj.backend.controller;

import edu.gyj.backend.Input.Node;
import edu.gyj.backend.Input.QueryDataInput;
import edu.gyj.backend.entity.classCS.ClassCSEntity;
import edu.gyj.backend.entity.classCS.ClassDayCSEntity;
import edu.gyj.backend.entity.classCS.ClassMonthCSEntity;
import edu.gyj.backend.entity.classCS.ClassTCSEntity;
import edu.gyj.backend.entity.college.CollegeCSEntity;
import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import edu.gyj.backend.entity.college.CollegeTCSEntity;
import edu.gyj.backend.entity.major.MajorCSEntity;
import edu.gyj.backend.entity.major.MajorDayCSEntity;
import edu.gyj.backend.entity.major.MajorMonthCSEntity;
import edu.gyj.backend.entity.major.MajorTCSEntity;
import edu.gyj.backend.result.*;
import edu.gyj.backend.service.ClassService;
import edu.gyj.backend.service.CollegeService;
import edu.gyj.backend.service.MajorService;
import edu.gyj.backend.service.SchoolUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/schoolUser2")
public class SchoolUserController2 {
    @Autowired
    SchoolUserService schoolUserServer;
    @Autowired
    CollegeService collegeService;
    @Autowired
    MajorService majorService;
    @Autowired
    ClassService classService;

    @ResponseBody
    @RequestMapping(value = "/getConsume", method = RequestMethod.POST)
    public Result getConsume(@RequestBody QueryDataInput queryDataInput) {
        List<Node> nodes = queryDataInput.getId();
        List<ConsumeResult> consumeResults = new ArrayList<>();
        for (Node node : nodes) {
            ConsumeResult consumeResult = new ConsumeResult(node.getValue(), node.getLabel());
            if (node.getLevel() == 1) {
                List<CollegeCSEntity> collegeCSEntities = collegeService.findByCollegeId(node.getValue());
                List<ConsumeData> consumeData = new ArrayList<>();
                collegeCSEntities.forEach(collegeCSEntity -> consumeData.add(
                        new ConsumeData(
                                collegeCSEntity.getConsumption_count(),
                                collegeCSEntity.getConsumption_total_money(),
                                collegeCSEntity.getConsumption_average_money(),
                                collegeCSEntity.getConsumption_student_average_money(),
                                collegeCSEntity.getStudent_count(),
                                collegeCSEntity.getConsumption_low_count(),
                                collegeCSEntity.getConsumption_high_count(),
                                collegeCSEntity.getStudent_low_count(),
                                collegeCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeData().addAll(consumeData);
            } else if (node.getLevel() == 2) {
                List<MajorCSEntity> majorCSEntities = majorService.findByMajorId(node.getValue());
                List<ConsumeData> consumeData = new ArrayList<>();
                majorCSEntities.forEach(majorCSEntity -> consumeData.add(
                        new ConsumeData(
                                majorCSEntity.getConsumption_count(),
                                majorCSEntity.getConsumption_total_money(),
                                majorCSEntity.getConsumption_average_money(),
                                majorCSEntity.getConsumption_student_average_money(),
                                majorCSEntity.getStudent_count(),
                                majorCSEntity.getConsumption_low_count(),
                                majorCSEntity.getConsumption_high_count(),
                                majorCSEntity.getStudent_low_count(),
                                majorCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeData().addAll(consumeData);
            } else if (node.getLevel() == 3) {
                List<ClassCSEntity> classCSEntities = classService.findByClassId(node.getValue());
                List<ConsumeData> consumeData = new ArrayList<>();
                classCSEntities.forEach(classCSEntity -> consumeData.add(
                        new ConsumeData(
                                classCSEntity.getConsumption_count(),
                                classCSEntity.getConsumption_total_money(),
                                classCSEntity.getConsumption_average_money(),
                                classCSEntity.getConsumption_student_average_money(),
                                classCSEntity.getStudent_count(),
                                classCSEntity.getConsumption_low_count(),
                                classCSEntity.getConsumption_high_count(),
                                classCSEntity.getStudent_low_count(),
                                classCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeData().addAll(consumeData);
            }
            consumeResults.add(consumeResult);
        }
        return new Result(1, "加载成功",consumeResults);
    }

    @ResponseBody
    @RequestMapping(value = "/getThreeMeals", method = RequestMethod.POST)
    public Result getThreeMeals(@RequestBody QueryDataInput queryDataInput) {
        List<Node> nodes = queryDataInput.getId();
        List<ConsumeResult> consumeResults = new ArrayList<>();
        for (Node node : nodes) {
            ConsumeResult consumeResult = new ConsumeResult(node.getValue(), node.getLabel());
            if (node.getLevel() == 1) {
                List<CollegeTCSEntity> collegeTCSEntities = collegeService.findThreeByCollegeId(node.getValue());
                List<ConsumeThreeData> consumeThreeData = new ArrayList<>();
                collegeTCSEntities.forEach(collegeTCSEntity -> consumeThreeData.add(
                        new ConsumeThreeData(
                                collegeTCSEntity.getConsumption_category(),
                                collegeTCSEntity.getConsumption_count(),
                                collegeTCSEntity.getConsumption_total_money(),
                                collegeTCSEntity.getConsumption_average_money(),
                                collegeTCSEntity.getConsumption_student_average_money(),
                                collegeTCSEntity.getStudent_count(),
                                collegeTCSEntity.getConsumption_low_count(),
                                collegeTCSEntity.getConsumption_high_count(),
                                collegeTCSEntity.getStudent_low_count(),
                                collegeTCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeThreeData().addAll(consumeThreeData);
            } else if (node.getLevel() == 2) {
                List<MajorTCSEntity> majorTCSEntities = majorService.findThreeByMajorId(node.getValue());
                List<ConsumeThreeData> consumeThreeData = new ArrayList<>();
                majorTCSEntities.forEach(majorTCSEntity -> consumeThreeData.add(
                        new ConsumeThreeData(
                                majorTCSEntity.getConsumption_category(),
                                majorTCSEntity.getConsumption_count(),
                                majorTCSEntity.getConsumption_total_money(),
                                majorTCSEntity.getConsumption_average_money(),
                                majorTCSEntity.getConsumption_student_average_money(),
                                majorTCSEntity.getStudent_count(),
                                majorTCSEntity.getConsumption_low_count(),
                                majorTCSEntity.getConsumption_high_count(),
                                majorTCSEntity.getStudent_low_count(),
                                majorTCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeThreeData().addAll(consumeThreeData);
            } else if (node.getLevel() == 3) {
                List<ClassTCSEntity> classTCSEntities = classService.findThreeByClassId(node.getValue());
                List<ConsumeThreeData> consumeThreeData = new ArrayList<>();
                classTCSEntities.forEach(classTCSEntity -> consumeThreeData.add(
                        new ConsumeThreeData(
                                classTCSEntity.getConsumption_category(),
                                classTCSEntity.getConsumption_count(),
                                classTCSEntity.getConsumption_total_money(),
                                classTCSEntity.getConsumption_average_money(),
                                classTCSEntity.getConsumption_student_average_money(),
                                classTCSEntity.getStudent_count(),
                                classTCSEntity.getConsumption_low_count(),
                                classTCSEntity.getConsumption_high_count(),
                                classTCSEntity.getStudent_low_count(),
                                classTCSEntity.getStudent_high_count()
                        )
                ));
                consumeResult.getConsumeThreeData().addAll(consumeThreeData);
            }
            consumeResults.add(consumeResult);
        }
        return new Result(1, "加载成功",consumeResults);
    }
}

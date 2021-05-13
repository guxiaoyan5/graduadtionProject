package edu.gyj.backend.controller;

import edu.gyj.backend.Input.DayInput;
import edu.gyj.backend.Input.MonthInput;
import edu.gyj.backend.bean.LevelDayCSBean;
import edu.gyj.backend.bean.LevelDayTCSBean;
import edu.gyj.backend.bean.LevelMonthCSBean;
import edu.gyj.backend.bean.LevelMonthTCSBean;
import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.result.Result;
import edu.gyj.backend.service.ClassService;
import edu.gyj.backend.service.CollegeService;
import edu.gyj.backend.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(value = "/schoolUser3")
public class SchoolUserController3 {
    @Autowired
    CollegeService collegeService;
    @Autowired
    MajorService majorService;
    @Autowired
    ClassService classService;

    private static class Id {
        private int id;

        public Id() {
        }

        public Id(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getColleges", method = RequestMethod.GET)
    public Result getColleges() {
        return new Result(1, "加载成功", collegeService.findAll());
    }

    @ResponseBody
    @RequestMapping(value = "/getMajors", method = RequestMethod.POST)
    public Result getMajors(@RequestBody Id id) {
        return new Result(1, "加载成功", majorService.getCollegeId(id.getId()));
    }

    @ResponseBody
    @RequestMapping(value = "/getClass", method = RequestMethod.POST)
    public Result getClass(@RequestBody Id id) {
        return new Result(1, "加载成功", classService.getMajorId(id.getId()));
    }

    @ResponseBody
    @RequestMapping(value = "/getCollegeData", method = RequestMethod.POST)
    public Result getCollegeMonthCSData(@RequestBody MonthInput monthInput) {
        List<LevelMonthCSBean> levelMonthCSBeanList = new ArrayList<>();
        List<LevelMonthCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (int id : monthInput.getIds()) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = collegeService.findByIdAndMonth(id, year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (int id : monthInput.getIds()) {
                for (int month = 1; month <= 12; month++) {
                    temp = collegeService.findByIdAndMonth(id, year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getCollegeDayCS", method = RequestMethod.POST)
    public Result getCollegeDayCS(@RequestBody DayInput dayInput) {
        List<LevelDayCSBean> levelDayCSBeans = new ArrayList<>();
        List<LevelDayCSBean> temp = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        for (int id : dayInput.getIds()) {
            for (int i = -1; i < 6; i++) {
                calendar.set(Calendar.DATE, day + i);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                temp = collegeService.findByIdAndDay(id, year, month, day1);
                levelDayCSBeans.addAll(temp);
            }
        }
        return new Result(1, "加载成功", levelDayCSBeans);
    }

    @ResponseBody
    @RequestMapping(value = "/getCollegeMonthTCS", method = RequestMethod.POST)
    public Result getCollegeMonthTCS(@RequestBody MonthInput monthInput) {
        List<LevelMonthTCSBean> levelMonthTCSBeanList = new ArrayList<>();
        List<LevelMonthTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (int id : monthInput.getIds()) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = collegeService.findTCSByIdAndMonth(id, year, month);
                    if (temp != null) {
                        levelMonthTCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (int id : monthInput.getIds()) {
                for (int month = 1; month <= 12; month++) {
                    temp = collegeService.findTCSByIdAndMonth(id, year, month);
                    if (temp != null) {
                        levelMonthTCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthTCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getCollegeDayTCS", method = RequestMethod.POST)
    public Result getCollegeDayTCS(@RequestBody DayInput dayInput) {
        List<LevelDayTCSBean> levelDayTCSBeans = new ArrayList<>();
        List<LevelDayTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        int id = dayInput.getId();
        for (int i = -1; i < 6; i++) {
            calendar.set(Calendar.DATE, day + i);
            int day1 = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            temp = collegeService.findTCSByIdAndDay(id, year, month, day1);
            levelDayTCSBeans.addAll(temp);
        }
        return new Result(1, "加载成功", levelDayTCSBeans);
    }

    @ResponseBody
    @RequestMapping(value = "/getMajorMonthCS", method = RequestMethod.POST)
    public Result getMajorMonthCS(@RequestBody MonthInput monthInput) {
        List<MajorEntity> majorEntities = majorService.getCollegeId(monthInput.getId());
        List<LevelMonthCSBean> levelMonthCSBeanList = new ArrayList<>();
        List<LevelMonthCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (MajorEntity majorEntity : majorEntities) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = majorService.findByIdAndMonth(majorEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (MajorEntity majorEntity : majorEntities) {
                for (int month = 1; month <= 12; month++) {
                    temp = majorService.findByIdAndMonth(majorEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getMajorDayCS", method = RequestMethod.POST)
    public Result getMajorDayCS(@RequestBody DayInput dayInput) {
        List<MajorEntity> majorEntities = majorService.getCollegeId(dayInput.getId());
        List<LevelDayCSBean> levelDayTCSBeans = new ArrayList<>();
        List<LevelDayCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        for (MajorEntity majorEntity : majorEntities) {
            for (int i = -1; i < 6; i++) {
                calendar.set(Calendar.DATE, day + i);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                temp = majorService.findByIdAndDay(majorEntity.getId(), year, month, day1);
                levelDayTCSBeans.addAll(temp);
            }
        }
        return new Result(1, "加载成功", levelDayTCSBeans);
    }

    @ResponseBody
    @RequestMapping(value = "/getMajorMonthTCS", method = RequestMethod.POST)
    public Result getMajorMonthTCS(@RequestBody MonthInput monthInput) {
        List<MajorEntity> majorEntities = majorService.getCollegeId(monthInput.getId());
        List<LevelMonthTCSBean> levelMonthCSBeanList = new ArrayList<>();
        List<LevelMonthTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (MajorEntity majorEntity : majorEntities) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = majorService.findTCSByIdAndMonth(majorEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (MajorEntity majorEntity : majorEntities) {
                for (int month = 1; month <= 12; month++) {
                    temp = majorService.findTCSByIdAndMonth(majorEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getMajorDayTCS", method = RequestMethod.POST)
    public Result getMajorDayTCS(@RequestBody DayInput dayInput) {
        List<LevelDayTCSBean> levelDayTCSBeans = new ArrayList<>();
        List<LevelDayTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        for (int i = -1; i < 6; i++) {
            calendar.set(Calendar.DATE, day + i);
            int day1 = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            temp = majorService.findTCSByIdAndDay(dayInput.getId(), year, month, day1);
            levelDayTCSBeans.addAll(temp);
        }
        return new Result(1, "加载成功", levelDayTCSBeans);
    }

    @ResponseBody
    @RequestMapping(value = "/getClassMonthCS", method = RequestMethod.POST)
    public Result getClassMonthCS(@RequestBody MonthInput monthInput) {
        List<ClassEntity> classEntities = classService.getMajorId(monthInput.getId());
        List<LevelMonthCSBean> levelMonthCSBeanList = new ArrayList<>();
        List<LevelMonthCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (ClassEntity classEntity : classEntities) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = classService.findByIdAndMonth(classEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (ClassEntity classEntity : classEntities) {
                for (int month = 1; month <= 12; month++) {
                    temp = classService.findByIdAndMonth(classEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getClassDayCS", method = RequestMethod.POST)
    public Result getClassDayCS(@RequestBody DayInput dayInput) {
        List<ClassEntity> classEntities = classService.getMajorId(dayInput.getId());
        List<LevelDayCSBean> levelDayTCSBeans = new ArrayList<>();
        List<LevelDayCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        for (ClassEntity classEntity : classEntities) {
            for (int i = -1; i < 6; i++) {
                calendar.set(Calendar.DATE, day + i);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                temp = classService.findByIdAndDay(classEntity.getId(), year, month, day1);
                levelDayTCSBeans.addAll(temp);
            }
        }
        return new Result(1, "加载成功", levelDayTCSBeans);
    }

    @ResponseBody
    @RequestMapping(value = "/getClassMonthTCS", method = RequestMethod.POST)
    public Result getClassMonthTCS(@RequestBody MonthInput monthInput) {
        List<ClassEntity> classEntities = classService.getMajorId(monthInput.getId());
        List<LevelMonthTCSBean> levelMonthCSBeanList = new ArrayList<>();
        List<LevelMonthTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(monthInput.getYear());
        int year = calendar.get(Calendar.YEAR);
        if (nowYear == year) {
            for (ClassEntity classEntity : classEntities) {
                for (int month = 1; month <= nowMonth; month++) {
                    temp = classService.findTCSByIdAndMonth(classEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        } else {
            for (ClassEntity classEntity : classEntities) {
                for (int month = 1; month <= 12; month++) {
                    temp = classService.findTCSByIdAndMonth(classEntity.getId(), year, month);
                    if (temp != null) {
                        levelMonthCSBeanList.addAll(temp);
                    }
                }
            }
        }
        return new Result(1, "加载成功", levelMonthCSBeanList);
    }

    @ResponseBody
    @RequestMapping(value = "/getClassDayTCS", method = RequestMethod.POST)
    public Result getClassDayTCS(@RequestBody DayInput dayInput) {
        List<LevelDayTCSBean> levelDayTCSBeans = new ArrayList<>();
        List<LevelDayTCSBean> temp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInput.getDay());
        int day = calendar.get(Calendar.DATE);
        for (int i = -1; i < 6; i++) {
            calendar.set(Calendar.DATE, day + i);
            int day1 = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            temp = classService.findTCSByIdAndDay(dayInput.getId(), year, month, day1);
            levelDayTCSBeans.addAll(temp);
        }
        return new Result(1, "加载成功", levelDayTCSBeans);
    }

}

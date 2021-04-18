package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.entity.college.CollegeEntity;
import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.entity.student.StudentEntity;
import edu.gyj.backend.mapper.classCS.ClassMapper;
import edu.gyj.backend.mapper.college.CollegeMapper;
import edu.gyj.backend.mapper.major.MajorMapper;
import edu.gyj.backend.mapper.student.StudentMapper;
import edu.gyj.backend.result.StudentResult;
import edu.gyj.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    MajorMapper majorMapper;
    @Autowired
    CollegeMapper collegeMapper;

    @Override
    public List<StudentResult> getAll() {
        List<StudentResult> studentResults = new ArrayList<>();
        List<StudentEntity> studentEntities = studentMapper.findAll();
        for (StudentEntity studentEntity : studentEntities) {
            ClassEntity classEntity = classMapper.findById(studentEntity.getClassId());
            MajorEntity majorEntity = majorMapper.findById(studentEntity.getMajorId());
            CollegeEntity collegeEntity = collegeMapper.findById(studentEntity.getCollegeId());
            studentResults.add(new StudentResult(
                    studentEntity.getId(),
                    studentEntity.getName(),
                    classEntity.getName(),
                    majorEntity.getMajor(),
                    collegeEntity.getCollege(),
                    studentEntity.getSex(),
                    classEntity.getId(),
                    majorEntity.getId(),
                    collegeEntity.getId()
            ));
        }
        return studentResults;
    }

    @Override
    public List<StudentResult> getByClassId(ClassEntity classEntity) {
        ClassEntity classEntity1 = classMapper.findById(classEntity.getId());
        MajorEntity majorEntity = majorMapper.findById(classMapper.findById(classEntity.getId()).getMajorId());
        CollegeEntity collegeEntity = collegeMapper.findById(majorEntity.getCollegeId());
        List<StudentEntity> studentEntities = studentMapper.findByClassId(classEntity.getId());
        List<StudentResult> studentResults = new ArrayList<>();
        for(StudentEntity studentEntity:studentEntities){
            studentResults.add(new StudentResult(
                    studentEntity.getId(),
                    studentEntity.getName(),
                    classEntity1.getName(),
                    majorEntity.getMajor(),
                    collegeEntity.getCollege(),
                    studentEntity.getSex(),
                    classEntity1.getId(),
                    majorEntity.getId(),
                    collegeEntity.getId()
            ));
        }
        return studentResults;
    }

    @Override
    public int add(StudentEntity studentEntity) {
        return studentMapper.insertStudent(studentEntity);
    }

    @Override
    public int update(StudentEntity studentEntity) {
        return studentMapper.updateStudent(studentEntity);
    }

    @Override
    public int delete(StudentEntity studentEntity) {
        return studentMapper.deleteStudent(studentEntity.getId());
    }
}

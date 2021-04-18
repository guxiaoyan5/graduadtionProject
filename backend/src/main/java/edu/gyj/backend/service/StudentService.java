package edu.gyj.backend.service;

import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.entity.student.StudentEntity;
import edu.gyj.backend.result.StudentResult;

import java.util.List;

public interface StudentService {
    public List<StudentResult> getAll();
    public List<StudentResult> getByClassId(ClassEntity classEntity);
    public int add(StudentEntity studentEntity);
    public int update(StudentEntity studentEntity);
    public int delete(StudentEntity studentEntity);
}

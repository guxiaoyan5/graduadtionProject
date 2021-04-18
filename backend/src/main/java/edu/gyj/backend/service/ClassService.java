package edu.gyj.backend.service;

import edu.gyj.backend.entity.classCS.ClassEntity;
import edu.gyj.backend.result.ClassResult;

import java.util.List;

public interface ClassService {
    public List<ClassResult> getAll();
    public int add(ClassEntity classEntity);
    public int update(ClassEntity classEntity);
    public int delete(ClassEntity classEntity);
}

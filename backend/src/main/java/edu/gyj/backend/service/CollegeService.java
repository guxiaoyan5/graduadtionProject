package edu.gyj.backend.service;

import edu.gyj.backend.entity.college.CollegeEntity;

import java.util.List;

public interface CollegeService {
    public List<CollegeEntity> findAll();
    public int add(CollegeEntity collegeEntity);
    public int update(CollegeEntity collegeEntity);
    public int delete(CollegeEntity collegeEntity);
}

package edu.gyj.backend.service;

import edu.gyj.backend.entity.major.MajorEntity;
import edu.gyj.backend.result.MajorResult;

import java.util.List;

public interface MajorService {
    public List<MajorResult> getAll();
    public int addMajor(MajorEntity majorEntity);
    public int update(MajorEntity majorEntity);
    public int delete(MajorEntity majorEntity);
}

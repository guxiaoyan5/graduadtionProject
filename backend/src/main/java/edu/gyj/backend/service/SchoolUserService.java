package edu.gyj.backend.service;

import edu.gyj.backend.entity.SchoolUserEntity;
import edu.gyj.backend.entity.student.StudentEntity;
import edu.gyj.backend.result.ResultSchoolUserLogin;
import org.springframework.stereotype.Service;

public interface SchoolUserService {
    public SchoolUserEntity login(String id);
}

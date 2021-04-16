package edu.gyj.backend.service;

import edu.gyj.backend.entity.SchoolUserEntity;

public interface SchoolUserService {
    public SchoolUserEntity login(String id);
    public int updatePassword(SchoolUserEntity schoolUserEntity);
    public int updateName(SchoolUserEntity schoolUserEntity);
}

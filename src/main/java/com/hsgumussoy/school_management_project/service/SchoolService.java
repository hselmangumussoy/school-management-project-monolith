package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.SchoolDto;
import com.hsgumussoy.school_management_project.entity.School;


public interface SchoolService {
    public SchoolDto save(SchoolDto dto);
    public SchoolDto get(String id);
    void delete(String id);
    SchoolDto update(String id, SchoolDto dto);
    public School findById(Long schoolId);



}

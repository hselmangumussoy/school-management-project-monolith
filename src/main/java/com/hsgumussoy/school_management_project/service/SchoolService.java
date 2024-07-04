package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.SchoolDto;
import com.hsgumussoy.school_management_project.entity.School;

import java.util.List;


public interface SchoolService {
    SchoolDto save(SchoolDto dto);
    SchoolDto get(String id);
    void delete(String id);
    SchoolDto update(String id, SchoolDto dto);
    List<SchoolDto> getAll();
    public School findById(Long schoolId);


}

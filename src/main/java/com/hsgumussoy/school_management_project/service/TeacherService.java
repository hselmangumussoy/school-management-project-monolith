package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto save(TeacherDto dto);
    TeacherDto get(String id);
    void delete(String id);
    TeacherDto update(String id, TeacherDto dto);
    List<TeacherDto> getAll();
}

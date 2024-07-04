package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.StudentDto;

public interface StudentService {
    public StudentDto save(StudentDto dto);

    public StudentDto get(String id);

    void delete(String id);
    StudentDto update(String id, StudentDto dto);
}

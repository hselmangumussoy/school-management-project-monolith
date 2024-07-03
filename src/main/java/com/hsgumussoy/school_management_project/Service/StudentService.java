package com.hsgumussoy.school_management_project.Service;

import com.hsgumussoy.school_management_project.dto.StudentDto;

public interface StudentService {
    public StudentDto save(StudentDto dto);

    public StudentDto get(String id);

    void delete(String id);
}

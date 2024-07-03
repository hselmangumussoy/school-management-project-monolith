package com.hsgumussoy.school_management_project.Service;

import com.hsgumussoy.school_management_project.dto.TeacherDto;
import com.hsgumussoy.school_management_project.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherService {
    TeacherDto save(TeacherDto dto);

    TeacherDto get(String id);

    void delete(String id);
}

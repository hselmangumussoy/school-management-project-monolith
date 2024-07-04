package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.entity.Classroom;

import java.util.List;

public interface ClassroomService {
    ClassroomDto save(ClassroomDto dto);
    ClassroomDto get(String id);
    void delete(String id);
    ClassroomDto update(String id, ClassroomDto dto);
    List<Classroom> findBySchoolId(Long schoolId);
    Classroom findById(Long classroomId);



}

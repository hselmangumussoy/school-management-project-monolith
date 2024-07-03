package com.hsgumussoy.school_management_project.Service;

import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.School;

import java.util.List;

public interface ClassroomService {
    ClassroomDto save(ClassroomDto dto);
    ClassroomDto get(String id);
    void delete(String id);
    List<Classroom> findBySchoolId(Long schoolId);
    Classroom findById(Long classroomId);



}

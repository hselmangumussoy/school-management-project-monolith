package com.hsgumussoy.school_management_project.dto;

import com.hsgumussoy.school_management_project.entity.School;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDto {
    private Long id;
    private String name;
    private List<StudentDto> studentList;
    private List<TeacherDto> teacherList;
    private Long schoolId;
}

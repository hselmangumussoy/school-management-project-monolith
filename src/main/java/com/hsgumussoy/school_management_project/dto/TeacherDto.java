package com.hsgumussoy.school_management_project.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    private Long id;
    private String name;
    private String birthPlace;
    private Date birthDay;
    private String email;
    private String branch;
    private Long classroomId;
}

package com.hsgumussoy.school_management_project.request;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest {
    private String name;
    private String birthPlace;
    private Date birthDay;
    private String email;
    private String branch;
    private Long classroomId;
}

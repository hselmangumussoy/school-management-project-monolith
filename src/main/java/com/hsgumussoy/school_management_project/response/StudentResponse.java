package com.hsgumussoy.school_management_project.response;

import lombok.*;

import java.util.Date;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private Date birthDay;
    private String birthPlace;
    private String tckn;
    private String schoolNo;
    private Long classroomId;
}

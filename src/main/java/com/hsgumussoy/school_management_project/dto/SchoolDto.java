package com.hsgumussoy.school_management_project.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    private Long id;
    private String name;
    private String address;
    private List<ClassroomDto> classroomList;
    private List<ManagerDto> managerList;
}

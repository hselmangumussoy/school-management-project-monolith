package com.hsgumussoy.school_management_project.response;

import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolResponse extends BaseResponse{
    private Long id;
    private String name;
    private String address;
    private List<ManagerDto> managerList;
    private List<ClassroomDto> classroomList;
}

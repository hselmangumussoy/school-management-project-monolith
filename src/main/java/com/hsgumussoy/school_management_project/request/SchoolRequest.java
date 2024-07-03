package com.hsgumussoy.school_management_project.request;

import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRequest {
    private String name;
    private String address;

}

package com.hsgumussoy.school_management_project.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequest {
    private String name;
    private Long schoolId;
}

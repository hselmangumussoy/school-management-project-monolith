package com.hsgumussoy.school_management_project.request;

import com.hsgumussoy.school_management_project.entity.School;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequest {
    private String name;
    private String birthPlace;
    private Date birthDay;
    private String email;
    private String branch;
    private Long schoolId;
}

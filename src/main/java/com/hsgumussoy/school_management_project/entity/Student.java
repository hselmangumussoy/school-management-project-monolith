package com.hsgumussoy.school_management_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date birthDay;
    private String birthPlace;
    private String tckn;
    private String schoolNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

}

package com.hsgumussoy.school_management_project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private List<Manager> managerList;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Classroom> classroomList;


}

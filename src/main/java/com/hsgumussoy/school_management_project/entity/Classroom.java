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
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Student> studentList;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Teacher> teacherList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private School school;

}

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
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String birthPlace;
    private Date birthDay;
    private String email;
    private String branch;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private School school;
}

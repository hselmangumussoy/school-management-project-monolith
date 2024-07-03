package com.hsgumussoy.school_management_project.repository;


import com.hsgumussoy.school_management_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}

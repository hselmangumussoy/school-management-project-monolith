package com.hsgumussoy.school_management_project.repository;


import com.hsgumussoy.school_management_project.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}

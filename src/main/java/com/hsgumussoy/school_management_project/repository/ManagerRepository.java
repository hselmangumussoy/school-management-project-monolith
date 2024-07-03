package com.hsgumussoy.school_management_project.repository;

import com.hsgumussoy.school_management_project.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    public List<Manager> findBySchoolId(Long schoolId);
}

package com.hsgumussoy.school_management_project.repository;

import com.hsgumussoy.school_management_project.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SchoolRepository extends JpaRepository<School,Long> {

}

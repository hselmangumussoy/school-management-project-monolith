package com.hsgumussoy.school_management_project.Service;

import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.entity.Manager;

import java.util.List;

public interface ManagerService {
    public ManagerDto save(ManagerDto dto);
    public ManagerDto get(String id);
    public List<Manager> findBySchoolId(Long schoolId) ;


    void delete(String id);
}

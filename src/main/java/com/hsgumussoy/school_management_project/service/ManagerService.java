package com.hsgumussoy.school_management_project.service;

import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.entity.Manager;

import java.util.List;

public interface ManagerService {
    public ManagerDto save(ManagerDto dto);

    public ManagerDto get(String id);

    public List<Manager> findBySchoolId(Long schoolId);

    ManagerDto update(String id, ManagerDto dto);

    List<ManagerDto> getAll();

    void delete(String id);


}

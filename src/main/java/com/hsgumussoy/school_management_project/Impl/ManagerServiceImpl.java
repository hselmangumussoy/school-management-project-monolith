package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.exceptions.RecordNotFoundExceptions;
import com.hsgumussoy.school_management_project.service.ManagerService;
import com.hsgumussoy.school_management_project.service.SchoolService;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.entity.Manager;
import com.hsgumussoy.school_management_project.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository repository;
    @Autowired
    @Lazy
    private SchoolService schoolService;
    public ManagerDto save(ManagerDto dto){
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public ManagerDto get(String id) {
        return toDto(repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RecordNotFoundExceptions(5000,"Manager not found!")));
    }

    @Override
    public void delete(String id) {
        Long managerId = Long.parseLong(id);
        removeFromSchool(managerId);
        repository.deleteById(managerId);
    }

    private void removeFromSchool(Long managerId) {
        Manager manager = repository.findById(managerId).orElseThrow();
        if (manager.getSchool() != null) {
            manager.getSchool().getManagerList().remove(manager);
        }
        manager.setSchool(null);
    }
    @Override
    public ManagerDto update(String id, ManagerDto dto) {
        Manager existManager = repository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("Manager not found with id "+id));

        existManager.setEmail(dto.getEmail());
        existManager.setBranch(dto.getBranch());
        existManager.setBirthPlace(dto.getBirthPlace());
        existManager.setName(dto.getName());
        existManager.setBirthDay(dto.getBirthDay());
        //existManager.setClassroom();

        return toDto(repository.save(existManager));
    }

    @Override
    public List<ManagerDto> getAll() {
        List<ManagerDto> managerDtoList =new ArrayList<>();

        for (Manager manager:repository.findAll() ){
            managerDtoList.add(toDto(manager));
        }
        return managerDtoList;
    }

    private Manager toEntity(ManagerDto dto) {
        return Manager.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .birthDay(dto.getBirthDay())
                .birthPlace(dto.getBirthPlace())
                .branch(dto.getBranch())
                .school(schoolService.findById(dto.getSchoolId()))
                .build();
    }
    private ManagerDto toDto(Manager manager) {
        return ManagerDto.builder()
                .id(manager.getId())
                .email(manager.getEmail())
                .branch(manager.getBranch())
                .birthDay(manager.getBirthDay())
                .birthPlace(manager.getBirthPlace())
                .schoolId(manager.getId())
                .name(manager.getName())
                .build();
    }

    public List<Manager> findBySchoolId(Long schoolId) {
        return repository.findBySchoolId(schoolId);

    }


}
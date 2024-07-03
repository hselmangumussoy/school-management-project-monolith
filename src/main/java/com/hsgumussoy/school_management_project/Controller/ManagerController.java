package com.hsgumussoy.school_management_project.Controller;

import com.hsgumussoy.school_management_project.Service.ManagerService;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.request.ManagerRequest;
import com.hsgumussoy.school_management_project.response.ManagerResponse;
import com.hsgumussoy.school_management_project.response.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("managers")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping
    private ManagerResponse save(@RequestBody ManagerRequest request){
        return toResponse(service.save(toDto(request)));
    }
    @GetMapping("/{id}")
    private ManagerResponse get(@PathVariable String id){
        return toResponse(service.get(id));
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id){
        service.delete(id);
    }

    private ManagerResponse toResponse(ManagerDto dto){
        return ManagerResponse.builder()
                .id(dto.getId())
                .schoolId(dto.getSchoolId())
                .birthDay(dto.getBirthDay())
                .birthPlace(dto.getBirthPlace())
                .branch(dto.getBranch())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }
    private ManagerDto toDto(ManagerRequest request) {
        return ManagerDto.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthDay(request.getBirthDay())
                .birthPlace(request.getBirthPlace())
                .branch(request.getBranch())
                .schoolId(request.getSchoolId())
                .build();
    }


}

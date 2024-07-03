package com.hsgumussoy.school_management_project.Controller;

import com.hsgumussoy.school_management_project.Service.SchoolService;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.dto.SchoolDto;
import com.hsgumussoy.school_management_project.entity.Manager;
import com.hsgumussoy.school_management_project.entity.School;
import com.hsgumussoy.school_management_project.request.SchoolRequest;
import com.hsgumussoy.school_management_project.response.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schools")
public class SchoolController {

    @Autowired
    private SchoolService service;

    @PostMapping
    private SchoolResponse save(@RequestBody SchoolRequest reqest ){
        return toResponse(service.save(toDto(reqest)));
    }
    @GetMapping("/{id}")
    private SchoolResponse get(@PathVariable String id){
        return toResponse(service.get(id));
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id){
        service.delete(id);
    }
    @PutMapping



    private SchoolDto toDto(SchoolRequest reqest) {
        return SchoolDto.builder()
                .name(reqest.getName())
                .address(reqest.getAddress())
                //.id(reqest.getClassroomId()) // emin değilim
                .build();
    }
    private SchoolResponse toResponse(SchoolDto dto){
        return SchoolResponse.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .name(dto.getName())
                .classroomList(dto.getClassroomList())
                .managerList(dto.getManagerList())
                .build();
    }

}

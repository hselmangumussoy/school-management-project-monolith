package com.hsgumussoy.school_management_project.controller;

import com.hsgumussoy.school_management_project.service.SchoolService;
import com.hsgumussoy.school_management_project.dto.SchoolDto;
import com.hsgumussoy.school_management_project.request.SchoolRequest;
import com.hsgumussoy.school_management_project.response.SchoolResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}")
    private SchoolResponse update(@PathVariable String id, @RequestBody SchoolRequest request){
        return toResponse(service.update(id,toDto(request)));
    }



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

package com.hsgumussoy.school_management_project.controller;

import com.hsgumussoy.school_management_project.request.ManagerRequest;
import com.hsgumussoy.school_management_project.response.ManagerResponse;
import com.hsgumussoy.school_management_project.service.StudentService;
import com.hsgumussoy.school_management_project.dto.StudentDto;
import com.hsgumussoy.school_management_project.request.StudentRequest;
import com.hsgumussoy.school_management_project.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentService service;
    @PostMapping
    private StudentResponse save(@RequestBody StudentRequest reqest ){
        return toResponse(service.save(toDto(reqest)));
    }
    @GetMapping("/{id}")
    private StudentResponse get(@PathVariable String id){
        return toResponse(service.get(id));
    }
    @PutMapping("/{id}")
    private StudentResponse update(@PathVariable String id, @RequestBody StudentRequest request){
        return toResponse(service.update(id,toDto(request)));
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id){
        service.delete(id);
    }
    private StudentResponse toResponse(StudentDto dto){
        return StudentResponse.builder()
                .id(dto.getId())
                .tckn(dto.getTckn())
                .birthPlace(dto.getBirthPlace())
                .name(dto.getName())
                .schoolNo(dto.getSchoolNo())
                .birthDay(dto.getBirthDay())
                .classroomId(dto.getClassroomId())
                .build();
    }
    private StudentDto toDto(StudentRequest reqest) {
        return StudentDto.builder()
                .schoolNo(reqest.getSchoolNo())
                .birthDay(reqest.getBirthDay())
                .tckn(reqest.getTckn())
                .birthPlace(reqest.getBirthPlace())
                .name(reqest.getName())
                .classroomId(reqest.getClassroomId())
                .build();
    }

}

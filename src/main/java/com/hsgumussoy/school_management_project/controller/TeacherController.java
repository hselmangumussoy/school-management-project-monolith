package com.hsgumussoy.school_management_project.controller;

import com.hsgumussoy.school_management_project.service.TeacherService;
import com.hsgumussoy.school_management_project.dto.TeacherDto;
import com.hsgumussoy.school_management_project.request.TeacherRequest;
import com.hsgumussoy.school_management_project.response.TeacherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("teachers")
public class TeacherController {
    @Autowired
    private TeacherService service;

    @PostMapping
    private TeacherResponse save(@RequestBody TeacherRequest request){
        return toResponse(service.save(toDto(request)));
    }
    @GetMapping("/{id}")
    private TeacherResponse get(@PathVariable String id){
        return toResponse(service.get(id));
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable String id){
        service.delete(id);
    }
    @PutMapping("/{id}")
    private TeacherResponse update(@PathVariable String id, @RequestBody TeacherRequest request){
        return toResponse(service.update(id,toDto(request)));
    }
    @GetMapping
    private List<TeacherResponse> getAll(){
        return mapTeacherResponses(service.getAll());
    }

    private List<TeacherResponse> mapTeacherResponses(List<TeacherDto> dtos) {
        return dtos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    private TeacherResponse toResponse(TeacherDto dto){
        return TeacherResponse.builder()
                .id(dto.getId())
                .birthDay(dto.getBirthDay())
                .birthPlace(dto.getBirthPlace())
                .branch(dto.getBranch())
                .email(dto.getEmail())
                .classroomId(dto.getClassroomId())
                .name(dto.getName())
                .build();
    }

    private TeacherDto toDto(TeacherRequest request) {
        return TeacherDto.builder()
                .email(request.getEmail())
                .name(request.getName())
                .branch(request.getBranch())
                .birthDay(request.getBirthDay())
                .birthPlace(request.getBirthPlace())
                .classroomId(request.getClassroomId())
                .build();
    }
}

package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.Service.ClassroomService;
import com.hsgumussoy.school_management_project.Service.TeacherService;
import com.hsgumussoy.school_management_project.dto.TeacherDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.Student;
import com.hsgumussoy.school_management_project.entity.Teacher;
import com.hsgumussoy.school_management_project.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository repository;
    @Autowired
    @Lazy
    private ClassroomService  classroomService;
    @Override
    public TeacherDto save(TeacherDto dto) {
        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public TeacherDto get(String id) {
        return toDto(repository.findById(Long.parseLong(id)).get());
    }

    @Override
    public void delete(String id) {
        Long teacherId = Long.parseLong(id);
        removeFromClassroom(teacherId);
        repository.deleteById(teacherId);
    }


    //BÖYLE BİR ŞEY KULLANMAK DOĞRU MU?
    private void removeFromClassroom(Long teacherId) {
        Teacher teacher = repository.findById(teacherId).orElseThrow();
        Classroom classroom = teacher.getClassroom();
        if (classroom != null) {
            classroom.getTeacherList().remove(teacher);
        }
        teacher.setClassroom(null);
    }

    private Teacher toEntity(TeacherDto dto) {
        return Teacher.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .birthPlace(dto.getBirthPlace())
                .branch(dto.getBranch())
                .birthDay(dto.getBirthDay())
                .classroom(classroomService.findById(dto.getClassroomId()))
                .build();
    }

    private TeacherDto toDto(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .name(teacher.getName())
                .birthPlace(teacher.getBirthPlace())
                .branch(teacher.getBranch())
                .birthDay(teacher.getBirthDay())
                .classroomId(teacher.getClassroom().getId())
                .build();
    }
}

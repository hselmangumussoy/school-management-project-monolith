package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.exceptions.RecordNotFoundExceptions;
import com.hsgumussoy.school_management_project.service.ClassroomService;
import com.hsgumussoy.school_management_project.service.TeacherService;
import com.hsgumussoy.school_management_project.dto.TeacherDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.Teacher;
import com.hsgumussoy.school_management_project.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return toDto(repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RecordNotFoundExceptions(5000,"Teacher not found!")));
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

    @Override
    public TeacherDto update(String id, TeacherDto dto) {
        Teacher existTeacher = repository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("Teacher not found with id: " + id));

        existTeacher.setBirthDay(dto.getBirthDay());
        existTeacher.setName(dto.getName());
        existTeacher.setEmail(dto.getEmail());
        existTeacher.setBirthPlace(dto.getBirthPlace());
        existTeacher.setBranch(dto.getBranch());
        //existTeacher.getClassroom().setName();  NASIL YAPILACAK BİLMİYORUM.

        return toDto(repository.save(existTeacher));
    }

    @Override
    public List<TeacherDto> getAll() {
        List<TeacherDto> teacherDtoList =new ArrayList<>();

        for (Teacher teacher:repository.findAll() ){
            teacherDtoList.add(toDto(teacher));
        }
        return teacherDtoList;
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

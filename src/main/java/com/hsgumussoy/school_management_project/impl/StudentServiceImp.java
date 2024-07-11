package com.hsgumussoy.school_management_project.impl;

import com.hsgumussoy.school_management_project.exceptions.RecordNotFoundExceptions;
import com.hsgumussoy.school_management_project.service.ClassroomService;
import com.hsgumussoy.school_management_project.service.StudentService;
import com.hsgumussoy.school_management_project.dto.StudentDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.Student;
import com.hsgumussoy.school_management_project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private ClassroomService classroomService;
    @Override
    public StudentDto save(StudentDto dto) {

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public StudentDto get(String id) {
        return toDto(repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RecordNotFoundExceptions(5000,"Student not found!")));
    }

    @Override
    public void delete(String id) {
        Long studentId = Long.parseLong(id);
        removeFromClassroom(studentId);
        repository.deleteById(studentId);
    }
    //BÖYLE BİR ŞEY KULLANMAK DOĞRU MU?
    private void removeFromClassroom(Long studentId) {
        Student student = repository.findById(studentId).orElseThrow();
        Classroom classroom = student.getClassroom();
        if (classroom != null) {
            classroom.getStudentList().remove(student);
        }
        student.setClassroom(null);
    }

    @Override
    public StudentDto update(String id, StudentDto dto) {
        Student existStudent = repository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("Student not found with id "+id));

        existStudent.setTckn(dto.getTckn());
        existStudent.setSchoolNo(dto.getSchoolNo());
        existStudent.setBirthPlace(dto.getBirthPlace());
        existStudent.setName(dto.getName());
        existStudent.setBirthDay(dto.getBirthDay());
        //existStudent.setClassroom();

        return toDto(repository.save(existStudent));
    }

    @Override
    public List<StudentDto> getAll() {
        List<StudentDto> studentDtoList =new ArrayList<>();

        for (Student student:repository.findAll() ){
            studentDtoList.add(toDto(student));
        }
        return studentDtoList;
    }


    private Student toEntity(StudentDto dto) {
        return Student.builder()
                .tckn(dto.getTckn())
                .id(dto.getId())
                .birthDay(dto.getBirthDay())
                .birthPlace(dto.getBirthPlace())
                .name(dto.getName())
                .schoolNo(dto.getSchoolNo())
                .classroom(classroomService.findById(dto.getClassroomId()))
                .build();
    }

    private StudentDto toDto(Student student) {
        return StudentDto.builder()
                .schoolNo(student.getSchoolNo())
                .id(student.getId())
                .tckn(student.getTckn())
                .birthPlace(student.getBirthPlace())
                .birthDay(student.getBirthDay())
                .name(student.getName())
                .classroomId(student.getClassroom().getId())
                .build();
    }
}

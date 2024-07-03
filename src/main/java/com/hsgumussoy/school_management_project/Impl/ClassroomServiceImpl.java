package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.Service.ClassroomService;
import com.hsgumussoy.school_management_project.Service.SchoolService;
import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.dto.StudentDto;
import com.hsgumussoy.school_management_project.dto.TeacherDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.School;
import com.hsgumussoy.school_management_project.entity.Student;
import com.hsgumussoy.school_management_project.entity.Teacher;
import com.hsgumussoy.school_management_project.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomRepository repository;
    @Autowired
    @Lazy
    private SchoolService schoolService;

    @Override
    public ClassroomDto save(ClassroomDto dto) {
        Classroom classroom = toEntity(dto);
        return toDto(repository.save(classroom));
    }

    @Override
    public ClassroomDto get(String id) {
        return toDto(repository.findById(Long.parseLong(id)).get());
    }

    @Override
    public void delete(String id) {
        Long classroomId = Long.parseLong(id);
        removeFromSchool(classroomId);
        repository.deleteById(classroomId);
    }

    private void removeFromSchool(Long classroomId) {
        Classroom classroom = repository.findById(classroomId).orElseThrow();
        if (classroom.getSchool() != null) {
            classroom.getSchool().getClassroomList().remove(classroom);
        }
        classroom.setSchool(null);
    }

    private Classroom toEntity(ClassroomDto dto) {
        return Classroom.builder()
                .name(dto.getName())
                .school(schoolService.findById(dto.getSchoolId()))
                .build();
    }

    private List<Teacher> mapTeachers(List<TeacherDto> teacherDtos) {
        if(teacherDtos == null){
            return null;
        }
        return teacherDtos.stream()
                .map(this::toTeacherEntity)
                .collect(Collectors.toList());
    }

    private Teacher toTeacherEntity(TeacherDto teacherDto) {
        return Teacher.builder()
                .name(teacherDto.getName())
                .email(teacherDto.getEmail())
                .id(teacherDto.getId())
                .birthPlace(teacherDto.getBirthPlace())
                .birthDay(teacherDto.getBirthDay())
                .branch(teacherDto.getBranch())
                .build();
    }

    private List<Student> mapStudents(List<StudentDto> studentDtos) {
        if (studentDtos ==null){
            return null;
        }
        return studentDtos.stream()
                .map(this::toStudentEntity)
                .collect(Collectors.toList());
    }

    private Student toStudentEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setBirthPlace(studentDto.getBirthPlace());
        student.setBirthDay(studentDto.getBirthDay());
        student.setTckn(studentDto.getTckn());
        student.setSchoolNo(studentDto.getSchoolNo());

        // Classroom entity's id is set correctly
        Classroom classroom = new Classroom();
        classroom.setId(studentDto.getClassroomId());
        student.setClassroom(classroom);

        return student;
    }

    private ClassroomDto toDto(Classroom classroom) {
        ClassroomDto dto = new ClassroomDto();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());

        if(classroom.getStudentList() != null){
            dto.setStudentList(mapStudentDtos(classroom.getStudentList()));
        }
        if(classroom.getTeacherList() != null){
            dto.setTeacherList(mapTeacherDtos(classroom.getTeacherList()));
        }
        dto.setSchoolId(classroom.getSchool().getId());

        return dto;
    }

    private List<StudentDto> mapStudentDtos(List<Student> students) {
        return students.stream()
                .map(this::toStudentDto)
                .collect(Collectors.toList());
    }

    private StudentDto toStudentDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .tckn(student.getTckn())
                .schoolNo(student.getSchoolNo())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .classroomId(student.getClassroom().getId())
                .name(student.getName())
                .build();
    }

    private List<TeacherDto> mapTeacherDtos(List<Teacher> teachers) {
        return teachers.stream()
                .map(this::toTeacherDto)
                .collect(Collectors.toList());
    }

    private TeacherDto toTeacherDto(Teacher teacher) {
        return TeacherDto.builder()
                .name(teacher.getName())
                .email(teacher.getEmail())
                .id(teacher.getId())
                .birthPlace(teacher.getBirthPlace())
                .birthDay(teacher.getBirthDay())
                .branch(teacher.getBranch())
                .build();
    }

    public List<Classroom> findBySchoolId(Long schoolId) {
        return repository.findBySchoolId(schoolId);
    }

    @Override
    public Classroom findById(Long classroomId) {
        if (classroomId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        return repository.findById(classroomId).orElseThrow();
    }

}

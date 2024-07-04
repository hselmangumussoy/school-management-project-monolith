package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.dto.*;
import com.hsgumussoy.school_management_project.entity.*;
import com.hsgumussoy.school_management_project.exceptions.RecordNotFoundExceptions;
import com.hsgumussoy.school_management_project.service.ClassroomService;
import com.hsgumussoy.school_management_project.service.ManagerService;
import com.hsgumussoy.school_management_project.service.SchoolService;
import com.hsgumussoy.school_management_project.repository.SchoolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository repository;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private ClassroomService classroomService;

    @Override
    public SchoolDto save(SchoolDto dto) {
        School school = toEntity(dto);

        List<Classroom> classrooms = classroomService.findBySchoolId(school.getId());
        school.setClassroomList(classrooms);

        List<Manager> managers = managerService.findBySchoolId(school.getId());
        school.setManagerList(managers);

        return toDto(repository.save(school));
    }

    @Override
    public SchoolDto get(String id) {
        return toDto(repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RecordNotFoundExceptions(5000,"School not found!")));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(Long.parseLong(id));
    }

    @Override
    @Transactional
    public SchoolDto update(String id, SchoolDto dto) {
        School exsistSchool = repository.findById(Long.parseLong(id))
                .orElseThrow(()-> new RuntimeException("School not found with id: " + id));

        exsistSchool.setName(dto.getName());
        exsistSchool.setAddress(dto.getAddress());

        exsistSchool.getManagerList().clear();
        if (dto.getManagerList() != null) {
            List<Manager> managers = dto.getManagerList().stream()
                    .map(this::toManagerEntity)
                    .toList();

            exsistSchool.getManagerList().addAll(managers);
        }
        else{
            throw new RecordNotFoundExceptions(5000, "Manager not found");
        }

        exsistSchool.getClassroomList().clear();
        if(dto.getClassroomList() != null){
            List<Classroom> classrooms = dto.getClassroomList().stream()
                    .map(this::toClassroomEntity)
                    .toList();

            exsistSchool.getClassroomList().addAll(classrooms);
        }
        else{
            throw new RecordNotFoundExceptions(5000, "Classroom not found");
        }

        return toDto(exsistSchool);
    }

    @Override
    public List<SchoolDto> getAll() {
        List<SchoolDto> schoolDtoList = new ArrayList<>();
        for (School school: repository.findAll()){
            schoolDtoList.add(toDto(school));
        }
        return schoolDtoList;
    }


    @Override
    public School findById(Long schoolId) {
        if (schoolId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        return repository.findById(schoolId).orElseThrow();
    }

    private School toEntity(SchoolDto dto) {
        School school = School.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .build();

        if (dto.getClassroomList() != null) {
            List<Classroom> classrooms = dto.getClassroomList().stream()
                    .map(this::toClassroomEntity)
                    .collect(Collectors.toList());
            school.setClassroomList(classrooms);
        }

        if (dto.getManagerList() != null) {
            List<Manager> managers = dto.getManagerList().stream()
                    .map(this::toManagerEntity)
                    .collect(Collectors.toList());
            school.setManagerList(managers);
        }

        return school;
    }

    private Classroom toClassroomEntity(ClassroomDto classroomDto) {
        Classroom classroom = new Classroom();
        classroom.setId(classroomDto.getId());
        classroom.setName(classroomDto.getName());
        classroom.getSchool().setId(classroom.getId());  //DİKKAT
        classroom.setStudentList(mapStudents(classroomDto.getStudentList())); // Değişiklik burada
        classroom.setTeacherList(mapTeachers(classroomDto.getTeacherList()));

        return classroom;
    }

    private List<Teacher> mapTeachers(List<TeacherDto> teacherDtos) {
        return teacherDtos.stream()
                .map(this::toTeachersEntity)
                .collect(Collectors.toList());
    }

    private Teacher toTeachersEntity(TeacherDto teacherDto) {
        Teacher teacher= new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setBranch(teacherDto.getBranch());
        teacher.setBirthPlace(teacherDto.getBirthPlace());
        teacher.setBirthDay(teacherDto.getBirthDay());
        teacher.getClassroom().setId(teacherDto.getClassroomId());
        return teacher;
    }

    private List<Student> mapStudents(List<StudentDto> studentDtos) {
        return studentDtos.stream()
                .map(this::toStudentEntity)
                .collect(Collectors.toList());
    }

    private Student toStudentEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setBirthDay(studentDto.getBirthDay());
        student.setBirthPlace(studentDto.getBirthPlace());
        student.getClassroom().setId(studentDto.getClassroomId());
        student.setSchoolNo(studentDto.getSchoolNo());
        student.setTckn(studentDto.getTckn());

        return student;
    }

    private Manager toManagerEntity(ManagerDto managerDto) {
        Manager manager = new Manager();
        manager.setId(managerDto.getId());
        manager.setName(managerDto.getName());
        manager.setBranch(managerDto.getBranch());
        manager.setEmail(managerDto.getEmail());
        manager.getSchool().setId(managerDto.getSchoolId());
        manager.setBirthPlace(managerDto.getBirthPlace());
        manager.setBirthDay(managerDto.getBirthDay());
        return manager;
    }

    private SchoolDto toDto(School school) {
        List<ClassroomDto> classroomDtos = mapClassrooms(school.getClassroomList());
        List<ManagerDto> managerDtos = mapManagers(school.getManagerList());

        return SchoolDto.builder()
                .id(school.getId())
                .name(school.getName())
                .address(school.getAddress())
                .classroomList(classroomDtos)
                .managerList(managerDtos)
                .build();
    }

    private List<ClassroomDto> mapClassrooms(List<Classroom> classrooms) {
        return classrooms.stream()
                .map(this::mapClassroom)
                .collect(Collectors.toList());
    }

    private ClassroomDto mapClassroom(Classroom classroom) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setName(classroom.getName());
        classroomDto.setStudentList(mapStudentsDto(classroom.getStudentList()));
        classroomDto.setTeacherList(mapTeachersDto(classroom.getTeacherList()));
        classroomDto.setSchoolId(classroom.getSchool().getId());

        return classroomDto;
    }

    private List<TeacherDto> mapTeachersDto(List<Teacher> students) {
        return students.stream()
                .map(this::mapTeachertDto)
                .collect(Collectors.toList());
    }

    private TeacherDto mapTeachertDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        teacherDto.setBirthDay(teacher.getBirthDay());
        teacherDto.setBirthPlace(teacher.getBirthPlace());
        teacherDto.setClassroomId(teacher.getClassroom().getId());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setBranch(teacher.getBranch());

        return teacherDto;
    }
    private List<StudentDto> mapStudentsDto(List<Student> students) {
        return students.stream()
                .map(this::mapStudentDto)
                .collect(Collectors.toList());
    }

    private StudentDto mapStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setBirthDay(student.getBirthDay());
        studentDto.setBirthPlace(student.getBirthPlace());
        studentDto.setClassroomId(student.getClassroom().getId());
        studentDto.setSchoolNo(student.getSchoolNo());
        studentDto.setTckn(student.getTckn());

        return studentDto;
    }

    private List<ManagerDto> mapManagers(List<Manager> managers) {
        return managers.stream()
                .map(this::mapManager)
                .collect(Collectors.toList());
    }

    private ManagerDto mapManager(Manager manager) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(manager.getId());
        managerDto.setName(manager.getName());
        managerDto.setBranch(manager.getBranch());
        managerDto.setEmail(manager.getEmail());
        managerDto.setBirthPlace(manager.getBirthPlace());
        managerDto.setBirthDay(manager.getBirthDay());
        managerDto.setSchoolId(manager.getSchool().getId());

        return managerDto;
    }
}

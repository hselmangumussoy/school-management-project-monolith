package com.hsgumussoy.school_management_project.Impl;

import com.hsgumussoy.school_management_project.Service.ClassroomService;
import com.hsgumussoy.school_management_project.Service.ManagerService;
import com.hsgumussoy.school_management_project.Service.SchoolService;
import com.hsgumussoy.school_management_project.Service.StudentService;
import com.hsgumussoy.school_management_project.dto.ClassroomDto;
import com.hsgumussoy.school_management_project.dto.ManagerDto;
import com.hsgumussoy.school_management_project.dto.SchoolDto;
import com.hsgumussoy.school_management_project.dto.StudentDto;
import com.hsgumussoy.school_management_project.entity.Classroom;
import com.hsgumussoy.school_management_project.entity.Manager;
import com.hsgumussoy.school_management_project.entity.School;
import com.hsgumussoy.school_management_project.entity.Student;
import com.hsgumussoy.school_management_project.repository.ClassroomRepository;
import com.hsgumussoy.school_management_project.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return toDto(repository.findById(Long.parseLong(id)).get());
    }

    @Override
    public void delete(String id) {
        repository.deleteById(Long.parseLong(id));
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

        return classroom;
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
        student.getClassroom().setId(studentDto.getId());
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
        classroomDto.setSchoolId(classroom.getSchool().getId());

        return classroomDto;
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

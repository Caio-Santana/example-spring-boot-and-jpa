package com.samsoftware.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService service;

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_save_student() {
        // Given
        StudentDto dto = new StudentDto(
                "John",
                "Doe",
                "john@mail.com",
                1
        );

        Student student = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );

        Student savedStudent = new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        );
        savedStudent.setId(1);

        // Mock the calls
        when(mapper.toStudent(dto))
                .thenReturn(student);

        when(repository.save(student))
                .thenReturn(savedStudent);

        when(mapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@mail.com"
                ));

        // When
        StudentResponseDto responseDto = service.saveStudent(dto);

        // Then
        assertEquals(dto.firstname(), responseDto.firstname());
        assertEquals(dto.lastname(), responseDto.lastname());
        assertEquals(dto.email(), responseDto.email());
        verify(mapper, times(1)).toStudent(any(StudentDto.class));
        verify(repository, times(1)).save(any(Student.class));
        verify(mapper, times(1)).toStudentResponseDto(any(Student.class));
    }

    @Test
    void should_find_all_students() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "John",
                "Doe",
                "john@mail.com",
                20
        ));

        // Mock the calls
        when(repository.findAll()).thenReturn(students);
        when(mapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@mail.com"
                ));

        // When
        List<StudentResponseDto> responseDtos = service.findAllStudents();

        // Then
        assertEquals(students.size(), responseDtos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void should_find_student_by_id() {
        // Given
        Integer studentId = 1;
        Student student = new Student(
                "John",
                "Smith",
                "john@mail.com",
                33
        );
        student.setId(studentId);

        // Mock calls
        when(repository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(mapper.toStudentResponseDto(student))
                .thenReturn(
                        new StudentResponseDto(
                                "John",
                                "Smith",
                                "john@mail.com"
                        )
                );

        // When
        StudentResponseDto responseDto = service.findById(studentId);

        // Then
        assertEquals(responseDto.firstname(), student.getFirstname());
        assertEquals(responseDto.lastname(), student.getLastname());
        assertEquals(responseDto.email(), student.getEmail());

        verify(repository).findById(studentId);
        verify(mapper).toStudentResponseDto(any(Student.class));
    }

    @Test
    void should_find_students_by_name() {
        // Given
        String studentFirstname = "John";

        List<Student> students = new ArrayList<>();
        students.add(new Student(
                studentFirstname,
                "Doe",
                "john@mail.com",
                20
        ));

        // Mock the calls
        when(repository.findAllByFirstnameContaining(studentFirstname))
                .thenReturn(students);
        when(mapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        studentFirstname,
                        "Doe",
                        "john@mail.com"
                ));

        // When
        List<StudentResponseDto> responseDtos = service.findStudentsByName(studentFirstname);

        // Then
        assertEquals(students.size(), responseDtos.size());
        verify(repository).findAllByFirstnameContaining(studentFirstname);
    }

    @Test
    void should_delete_student_by_id() {
        // Given
        Integer studentId = 1;

        // When
        service.deleteById(studentId);

        // Then
        verify(repository).deleteById(studentId);

    }
}
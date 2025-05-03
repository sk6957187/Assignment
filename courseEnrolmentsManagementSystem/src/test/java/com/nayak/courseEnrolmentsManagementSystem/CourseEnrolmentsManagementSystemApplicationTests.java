package com.nayak.courseEnrolmentsManagementSystem;

import com.nayak.courseEnrolmentsManagementSystem.dao.DAOStudent;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.service.StudentService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@SpringBootTest
class CourseEnrolmentsManagementSystemApplicationTests {

    @Mock
    private DAOStudent dao;

    @InjectMocks
    private StudentService studentService;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @org.junit.Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setName("Raja Kumar");
        student.setEmail("raja@gmail.com");
        student.setDate_Of_Birth(LocalDate.parse("2000-01-02"));
        when(dao.saveStudent(student)).thenReturn("success");

        String result = studentService.saveStudent(student);

        Assertions.assertEquals("success", result);
//        verify(dao, times(1)).saveStudent(student);
    }

}
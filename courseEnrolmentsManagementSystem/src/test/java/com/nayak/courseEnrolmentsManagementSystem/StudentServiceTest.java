package com.nayak.courseEnrolmentsManagementSystem;

import com.nayak.courseEnrolmentsManagementSystem.dao.DAOStudent;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

//@Disabled

@SpringBootTest
public class StudentServiceTest {

    @Mock
    private DAOStudent dao;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setName("Raja Kumar");
        student.setEmail("raja@gmail.com");
        student.setDate_Of_Birth(LocalDate.parse("2000-01-02"));
        when(dao.saveStudent(student)).thenReturn("Student saved");

        String result = studentService.saveStudent(student);

        assertEquals("success", result);
        verify(dao, times(1)).saveStudent(student);
    }

//    @Test
//    public void testGetStudent() {
//        List<Student> students = Arrays.asList(
//                new Student(1, "John", "Doe"),
//                new Student(2, "Jane", "Smith")
//        );
//        when(dao.getStudent()).thenReturn(students);
//
//        List<Student> result = studentService.getStudent();
//
//        assertEquals(2, result.size());
//        assertEquals("John", result.get(0).getFirstName());
//        verify(dao, times(1)).getStudent();
//    }

//    @Test
//    public void testGetStudentById() {
//        Student student = new Student(1, "John", "Doe");
//        when(dao.getStudentById(1)).thenReturn(student);
//
//        Student result = studentService.getStudentById(1);
//
//        assertNotNull(result);
//        assertEquals("John", result.getFirstName());
//        verify(dao, times(1)).getStudentById(1);
//    }

//    @Test
//    public void testUpdateStdDt() {
//        Student student = new Student(1, "John", "Updated");
//        when(dao.updateStdDa(student)).thenReturn(student);
//
//        Student result = studentService.updateStdDt(student);
//
//        assertNotNull(result);
//        assertEquals("Updated", result.getLastName());
//        verify(dao, times(1)).updateStdDa(student);
//    }
}

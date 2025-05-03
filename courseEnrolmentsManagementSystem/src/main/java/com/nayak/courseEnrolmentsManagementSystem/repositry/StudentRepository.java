package com.nayak.courseEnrolmentsManagementSystem.repositry;

import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

//    @Query("select e.student from Enrolment e where e.course.instructor.id = ?1")
//    List<Student> getStudentsByInstructorId(int id);

    @Query("select e.course from Enrolment e where e.student.id=?1")
    List<Course> getCourseByStudentId(int id);
}

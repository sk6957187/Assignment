package com.nayak.courseEnrolmentsManagementSystem.repositry;

import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query("select e.student from Enrolment e where e.course.instructor.id = ?1")
    List<Student> getStudentsByInstructorId(int id);

    @Query("select i.course from Instructor i where i.id = ?1")
    List<Course> getCourseByInstructoreId(int id);


}

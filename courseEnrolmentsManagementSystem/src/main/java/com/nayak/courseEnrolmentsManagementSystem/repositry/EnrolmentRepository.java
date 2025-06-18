package com.nayak.courseEnrolmentsManagementSystem.repositry;

import com.nayak.courseEnrolmentsManagementSystem.model.Enrolment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {
    @Query("select e from Enrolment e where e.student.id=?1")
    List<Enrolment> getEnrolmentByStudentId(int id);

    @Query("select e from Enrolment e where e.course.id=?1")
    List<Enrolment> getEnrolmentByCourseId(int id);
}

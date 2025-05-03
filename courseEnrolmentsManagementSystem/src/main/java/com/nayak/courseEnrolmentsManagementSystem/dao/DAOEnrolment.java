package com.nayak.courseEnrolmentsManagementSystem.dao;

import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.Enrolment;
import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.repositry.CourseRepository;
import com.nayak.courseEnrolmentsManagementSystem.repositry.EnrolmentRepository;
import com.nayak.courseEnrolmentsManagementSystem.repositry.StudentRepository;
import jakarta.persistence.Embeddable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DAOEnrolment {
    private static final Logger logger = LoggerFactory.getLogger(DAOEnrolment.class);
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public CourseRepository courseRepository;
    @Autowired
    private EnrolmentRepository enrolmentRepository;

    public String saveEnrolment(Enrolment enrolment) {
        Optional<Student> optStd = studentRepository.findById(enrolment.getStudent().getId());
        Optional<Course> optCrs = courseRepository.findById(enrolment.getCourse().getId());
        if(optCrs.isPresent() && optStd.isPresent()){
            enrolment.setCourse(optCrs.get());
            enrolment.setStudent(optStd.get());
            enrolmentRepository.save(enrolment);
            return "success";
        }else if(optCrs.isEmpty()){
            return "course are not available";
        } else {
            return "student are not valid";
        }
    }

    public List<Enrolment> getAllEnrolment() {
        List<Enrolment> le = enrolmentRepository.findAll();
        return le;
    }

    public Enrolment deleteEnrolment(int id) {
        Optional<Enrolment> opt = enrolmentRepository.findById(id);
        if(opt.isPresent()){
            enrolmentRepository.deleteById(id);
            return opt.get();
        }else{
            return null;
        }
    }

    public Enrolment updateById(int id, Enrolment enrolment) {
        Optional<Enrolment> opt = enrolmentRepository.findById(id);
        if (opt.isPresent()) {
            Enrolment existing = opt.get();
            Enrolment tempEnr = new Enrolment();
            tempEnr.setId(id);
            tempEnr.setEnrolledDate(existing.getEnrolledDate());
            Student newStudent = enrolment.getStudent();
            if (newStudent != null && newStudent.getId() != 0) {
                Optional<Student> optStd = studentRepository.findById(newStudent.getId());
                if (optStd.isPresent()) {
                    tempEnr.setStudent(optStd.get());
                } else {
                    tempEnr.setStudent(existing.getStudent());
                }
            } else {
                tempEnr.setStudent(existing.getStudent());
            }
            Course newCourse = enrolment.getCourse();
            if (newCourse != null && newCourse.getId() != 0) {
                Optional<Course> optCrs = courseRepository.findById(newCourse.getId());
                if (optCrs.isPresent()) {
                    tempEnr.setCourse(optCrs.get());
                } else {
                    tempEnr.setCourse(existing.getCourse());
                }
            } else {
                tempEnr.setCourse(existing.getCourse());
            }

            return enrolmentRepository.save(tempEnr);
        } else {
            return null;
        }
    }

    public List<Enrolment> getEnrolmentByStudentId(int id) {
        List<Enrolment> le = enrolmentRepository.getEnrolmentByStudentId(id);
        if(le.size() > 0){
            return le;
        } else {
            return null;
        }
    }

    public List<Enrolment> getEnrolmentByCourseId(int cId) {
        List<Enrolment> le = enrolmentRepository.getEnrolmentByCourseId(cId);
        if(le.size() > 0){
            return le;
        } else {
            return null;
        }
    }
}

package com.nayak.courseEnrolmentsManagementSystem.dao;

import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;
import com.nayak.courseEnrolmentsManagementSystem.repositry.CourseRepository;
import com.nayak.courseEnrolmentsManagementSystem.repositry.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DAOCourse {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    public String saveCourse(Course course) {
        Optional<Instructor> optInst = instructorRepository.findById(course.getInstructor().getId());
        if(optInst.isPresent()){
            course.setInstructor(optInst.get());
            courseRepository.save(course);
            return "success";
        } else {
            return "instructor are not available";
        }
    }

    public List<Course> getAllCourse() {
        List<Course> cl = courseRepository.findAll();

        if(cl.size() > 0){
            return cl;
        } else {
            return null;
        }
    }


    public Course getCourseById(int id) {
        Optional<Course> opt = courseRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }else {
            return null;
        }
    }

    public Course updateCourse(Course course) {
        Optional<Course> opt = courseRepository.findById(course.getId());
        if (!opt.isPresent()) {
            return null; // or throw exception
        }

        Course existing = opt.get();

        if (course.getFee() != 0) {
            existing.setFee(course.getFee());
        }

        if (course.getTitle() != null) {
            existing.setTitle(course.getTitle());
        }

        if (course.getDuration() != 0) {
            existing.setDuration(course.getDuration());
        }

        if (course.getInstructor() != null && course.getInstructor().getId() != 0) {
            Optional<Instructor> optInstructor = instructorRepository.findById(course.getInstructor().getId());
            if (optInstructor.isPresent()) {
                existing.setInstructor(optInstructor.get());
            } else {
                return null; // or handle error
            }
        }

        courseRepository.save(existing);
        return existing;
    }

    public Course deleteCourseById(int id) {
        if(courseRepository.existsById(id)){
            Optional<Course> opt = courseRepository.findById(id);
            courseRepository.deleteById(id);
            return opt.get();
        } else {
            return null;
        }
    }

    public List<Course> getAllCourseByInstId(int id) {
        List<Course> cl = instructorRepository.getCourseByInstructoreId(id);
        return cl;
    }
}

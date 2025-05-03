package com.nayak.courseEnrolmentsManagementSystem.dao;  //(data_access_object)

import com.nayak.courseEnrolmentsManagementSystem.model.Course;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.repositry.CourseRepository;
import com.nayak.courseEnrolmentsManagementSystem.repositry.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class DAOStudent {
    private static final Logger logger = LoggerFactory.getLogger(DAOStudent.class);
    @Autowired
    private StudentRepository studentRepository;


    public String saveStudent(Student student) {
        logger.info("student data at DAO: {}", student);
        if(student != null){
            logger.info("Student: {}", student);
            studentRepository.save(student);
            return "success";
        } else {
            return "failed";
        }

    }

    public List<Student> getStudent() {
        List<Student> sl = studentRepository.findAll();
        logger.info("student data: {}",sl);
        return sl;
    }

    public Student getStudentById(int id) {
        Optional<Student> opt = studentRepository.findById(id);
        if(opt.isPresent()) {
            logger.info("Student data: {}", opt.get());
            return opt.get();
        } else {
            logger.warn("Student not found with ID: {}", id);
            return null;
        }
    }

    public Student updateStdDa(Student std){
        logger.info("{}",std);  //Student{id=7, name='Mohan Kumar', email='sumit@gmail.com', date_Of_Birth=null}
        Student stdTemp = new Student();
        Optional<Student> opt = studentRepository.findById(std.getId());
        if(opt.isPresent()) {
            if (std.getId() != 0) {
                stdTemp.setId(std.getId());
            }
            if (std.getName() != null) {
                stdTemp.setName(std.getName());
            } else {
                stdTemp.setName(opt.get().getName());
            }
            if (std.getEmail() != null) {
                stdTemp.setEmail(std.getEmail());
            } else {
                stdTemp.setEmail(opt.get().getEmail());
            }
            if (std.getDate_Of_Birth() != null) {
                stdTemp.setDate_Of_Birth(std.getDate_Of_Birth());
            } else {
                stdTemp.setDate_Of_Birth(opt.get().getDate_Of_Birth());
            }

            studentRepository.save(std);
            return stdTemp;

        }
        return null;
    }

    public Student deleteStd(int id) {
        Optional<Student> opt = studentRepository.findById(id);
        boolean exits = studentRepository.existsById(id);
        logger.info("student exits: {} id : {}", exits,id);
        if(opt.isPresent()){
            studentRepository.deleteById(id);
            return opt.get();
        }else {
            return null;
        }
    }


    public List<Course> getCourseByStudentId(int id) {
        List<Course> lc = studentRepository.getCourseByStudentId(id);
        if(lc.size() > 0){
            return lc;
        } else {
            return null;
        }
    }
}

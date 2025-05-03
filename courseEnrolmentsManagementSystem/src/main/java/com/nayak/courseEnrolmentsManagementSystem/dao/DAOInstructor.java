package com.nayak.courseEnrolmentsManagementSystem.dao;

import com.nayak.courseEnrolmentsManagementSystem.model.Instructor;
import com.nayak.courseEnrolmentsManagementSystem.model.Student;
import com.nayak.courseEnrolmentsManagementSystem.repositry.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DAOInstructor {
    private static final Logger logger = LoggerFactory.getLogger(DAOInstructor.class);

    @Autowired
    private InstructorRepository instructorRepository;
    public String saveInstructor(Instructor instructor) {
        if(instructor != null){
            instructorRepository.save(instructor);
            return "success";
        } else {
            return "failed";
        }
    }

    public List<Instructor> getInstructor() {
        List<Instructor> insList = instructorRepository.findAll();
        if(insList.size()>0){
            return insList;
        }else {
            return null;
        }
    }

    public Instructor getInstructorById(int id) {
        if(id == 0){
            return null;
        }
        if(instructorRepository.existsById(id)){
            Optional<Instructor> opt = instructorRepository.findById(id);
            return opt.get();
        }else {
            return null;
        }
    }

    public Instructor updateInstructor(Instructor instructor) {
        Instructor instTemp = new Instructor();
        Optional<Instructor> opt = instructorRepository.findById(instructor.getId());
        if(opt.isPresent()) {
            if (instructor.getId() != 0) {
                instTemp.setId(instructor.getId());
            }
            if (instructor.getName() != null) {
                instTemp.setName(instructor.getName());
            } else {
                instTemp.setName(opt.get().getName());
            }
            if (instructor.getEmail() != null) {
                instTemp.setEmail(instructor.getEmail());
            } else {
                instTemp.setEmail(opt.get().getEmail());
            }
            if (instructor.getSpecialization() != null) {
                instTemp.setSpecialization(instructor.getSpecialization());
            } else {
                instTemp.setSpecialization(opt.get().getSpecialization());
            }

            instructorRepository.save(instTemp);
            return instTemp;

        }
        return null;
    }

    public Instructor deleteInstructorById(int id) {
        Optional<Instructor> opt = instructorRepository.findById(id);
        if(opt.isPresent()){
            instructorRepository.deleteById(id);
            return opt.get();
        }else {
            return null;
        }
    }

    public List<Student> getStudentByInstId(int id) {
        List<Student> sl = instructorRepository.getStudentsByInstructorId(id);
        return sl;
    }
}

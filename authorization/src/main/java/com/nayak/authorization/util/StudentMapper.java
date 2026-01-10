package com.nayak.authorization.util;

import com.nayak.authorization.dto.StudentDTO;
import com.nayak.authorization.entity.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setRollNumber(student.getRollNumber());

        if (student.getTeacher() != null) {
            dto.setTeacherId(student.getTeacher().getTeacherId());
        }
        return dto;
    }

    public static Student toEntity(StudentDTO dto) {
        if (dto == null) return null;

        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setName(dto.getName());
        student.setRollNumber(dto.getRollNumber());

        return student;
    }
}

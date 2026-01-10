package com.nayak.authorization.util;

import com.nayak.authorization.dto.TeacherDTO;
import com.nayak.authorization.entity.Student;
import com.nayak.authorization.entity.Teacher;

import java.util.stream.Collectors;

public class TeacherMapper {

    public static TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) return null;

        TeacherDTO dto = new TeacherDTO();
        dto.setTeacherId(teacher.getTeacherId());
        dto.setName(teacher.getName());
        dto.setSubject(teacher.getSubject());

        if (teacher.getHod() != null) {
            dto.setHodId(teacher.getHod().getHodId());
        }

        if (teacher.getStudents() != null) {
            dto.setStudentIds(
                    teacher.getStudents()
                            .stream()
                            .map(Student::getStudentId)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static Teacher toEntity(TeacherDTO dto) {
        if (dto == null) return null;

        Teacher teacher = new Teacher();
        teacher.setTeacherId(dto.getTeacherId());
        teacher.setName(dto.getName());
        teacher.setSubject(dto.getSubject());

        return teacher;
    }
}

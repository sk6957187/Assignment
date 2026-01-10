package com.nayak.authorization.service;

import com.nayak.authorization.dto.StudentDTO;
import com.nayak.authorization.entity.Student;
import com.nayak.authorization.entity.Teacher;
import com.nayak.authorization.repository.StudentRepository;
import com.nayak.authorization.repository.TeacherRepository;
import com.nayak.authorization.util.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentDTO createStudent(StudentDTO dto) {
        Student student = StudentMapper.toEntity(dto);

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        student.setTeacher(teacher);

        return StudentMapper.toDTO(studentRepository.save(student));
    }
}

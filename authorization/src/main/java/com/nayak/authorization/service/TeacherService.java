package com.nayak.authorization.service;

import com.nayak.authorization.dto.TeacherDTO;
import com.nayak.authorization.entity.HOD;
import com.nayak.authorization.entity.Teacher;
import com.nayak.authorization.repository.HODRepository;
import com.nayak.authorization.repository.TeacherRepository;
import com.nayak.authorization.util.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final HODRepository hodRepository;

    public TeacherDTO createTeacher(TeacherDTO dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);

        HOD hod = hodRepository.findById(dto.getHodId())
                .orElseThrow(() -> new RuntimeException("HOD not found"));
        teacher.setHod(hod);

        return TeacherMapper.toDTO(teacherRepository.save(teacher));
    }
}

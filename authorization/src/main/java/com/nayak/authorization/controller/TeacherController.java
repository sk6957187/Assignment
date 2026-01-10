package com.nayak.authorization.controller;

import com.nayak.authorization.dto.TeacherDTO;
import com.nayak.authorization.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public TeacherDTO create(@RequestBody TeacherDTO dto) {
        return teacherService.createTeacher(dto);
    }
}


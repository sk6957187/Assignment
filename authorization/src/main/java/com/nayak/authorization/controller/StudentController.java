package com.nayak.authorization.controller;

import com.nayak.authorization.dto.StudentDTO;
import com.nayak.authorization.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentDTO create(@RequestBody StudentDTO dto) {
        return studentService.createStudent(dto);
    }
}


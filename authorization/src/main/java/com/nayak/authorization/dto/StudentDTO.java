package com.nayak.authorization.dto;

import lombok.Data;

@Data
public class StudentDTO {

    private Long studentId;
    private String name;
    private String rollNumber;

    // Parent reference
    private Long teacherId;
}

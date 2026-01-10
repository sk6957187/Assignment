package com.nayak.authorization.dto;

import lombok.Data;
import java.util.List;

@Data
public class TeacherDTO {

    private Long teacherId;
    private String name;
    private String subject;

    // Parent reference
    private Long hodId;

    // Child references
    private List<Long> studentIds;
}

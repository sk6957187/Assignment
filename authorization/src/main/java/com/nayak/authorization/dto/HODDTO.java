package com.nayak.authorization.dto;

import lombok.Data;
import java.util.List;

@Data
public class HODDTO {

    private Long hodId;
    private String name;
    private String department;

    // Parent reference
    private Long principalId;

    // Child references
    private List<Long> teacherIds;
}

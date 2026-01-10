package com.nayak.authorization.dto;

import lombok.Data;
import java.util.List;

@Data
public class PrincipalDTO {

    private Long principalId;
    private String name;
    private String email;

    // Child references (IDs only)
    private List<Long> hodIds;
}

package io.mountblue.BlogApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String name;
    private Long id;
    private String email;
}

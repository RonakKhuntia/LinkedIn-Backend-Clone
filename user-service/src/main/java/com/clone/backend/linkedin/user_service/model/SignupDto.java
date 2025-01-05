package com.clone.backend.linkedin.user_service.model;

import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String email;
    private String password;
}

package com.clone.backend.linkedin.user_service.model;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}

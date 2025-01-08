package com.clone.backend.linkedin.posts_service.model;

import lombok.Data;

@Data
public class PersonDto {
    private Long id;
    private Long userId;
    private String name;
}

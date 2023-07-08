package com.example.practice_api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StudentDto {
    private Integer id;
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;
}

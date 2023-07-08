package com.example.practice_api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private Integer age;

    @NotEmpty
    private String status;

    @NotEmpty
    private String role;
}

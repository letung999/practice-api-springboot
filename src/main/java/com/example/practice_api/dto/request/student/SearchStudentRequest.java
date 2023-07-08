package com.example.practice_api.dto.request.student;

import lombok.Data;

@Data
public class SearchStudentRequest {
    private String firstName;
    private String lastName;
    private String email;
}

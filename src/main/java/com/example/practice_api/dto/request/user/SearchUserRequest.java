package com.example.practice_api.dto.request.user;

import com.example.practice_api.dto.request.BaseSearchRequest;
import lombok.Data;

@Data
public class SearchUserRequest {
    private String name;

    private String address;

    private String gender;

    private String phoneNumber;

    private String email;

    private Integer age;

    private BaseSearchRequest searchRequest;

}

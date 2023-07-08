package com.example.practice_api.dto.response.user;

import com.example.practice_api.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchUserResponse {

    private List<User> users;

    private Long numOfItems;

    private Integer numberOfPages;

    @JsonProperty("index")
    private Integer pageIndex;

    @JsonProperty("size")
    private Integer pageSize;
}

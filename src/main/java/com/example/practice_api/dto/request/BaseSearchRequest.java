package com.example.practice_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BaseSearchRequest {

    @NotNull
    private Integer index;

    @NotNull
    private Integer size;

    private String sortBy;

    @JsonProperty
    private boolean isAscending;
}

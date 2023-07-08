package com.example.practice_api.exception;

import com.example.practice_api.constants.StatusConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingItemException extends RuntimeException {
    private String resources;
    private String fieldName;
    private String fieldValue;

    public ExistingItemException(String resources, String fieldName, String fieldValue) {
        super(String.format("%s " + StatusConstant.DATA_IS_EXIST + " %s with: %s", resources, fieldName, fieldValue));
        this.resources = resources;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

package com.example.practice_api.utils;

import com.example.practice_api.exception.ErrorDetails;
import com.example.practice_api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class MessageUtil {
    public static ResponseEntity<String> getResponseStatus(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "\"}", httpStatus);
    }
}

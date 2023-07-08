package com.example.practice_api.controller;

import com.example.practice_api.constants.StatusConstant;
import com.example.practice_api.dto.request.student.SearchStudentRequest;
import com.example.practice_api.entities.Student;
import com.example.practice_api.service.impl.StudentService;
import com.example.practice_api.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping("/add-student")
    ResponseEntity<String> addStudent(@RequestBody @Valid Student student) {
        try {
            return studentService.addStudent(student);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtil.getResponseStatus(StatusConstant.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/filter-by")
    ResponseEntity<List<Student>> filterStudents(@RequestBody @Valid SearchStudentRequest request) {
        try {
            return studentService.getStudent(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.example.practice_api.service.contract;

import com.example.practice_api.dto.request.student.SearchStudentRequest;
import com.example.practice_api.entities.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStudentService {

    /**
     * add student in database
     * @param student
     * @return
     */
    ResponseEntity<String> addStudent(Student student);

    /**
     * filter student by condition
     *
     * @param request
     * @return students ha
     */
    ResponseEntity<List<Student>> getStudent(SearchStudentRequest request);
}

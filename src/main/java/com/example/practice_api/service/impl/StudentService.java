package com.example.practice_api.service.impl;

import com.example.practice_api.constants.StatusConstant;
import com.example.practice_api.dto.StudentDto;
import com.example.practice_api.dto.request.student.SearchStudentRequest;
import com.example.practice_api.entities.Student;
import com.example.practice_api.repository.StudentRepository;
import com.example.practice_api.service.contract.IStudentService;
import com.example.practice_api.utils.MessageUtil;
import com.example.practice_api.utils.MockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<String> addStudent(Student student) {
        try {
//            var userDTOList = MockData.getData("persons.json", UserDTO[].class);
//            var resultData = new ArrayList<Student>();
//            for (var item : userDTOList) {
//                var std = new Student();
//                std.setFirstName(item.getFirstName());
//                std.setLastName(item.getLastName());
//                std.setEmail(item.getEmail());
//                resultData.add(std);
//            }

            var resultData = MockData.getData("persons.json", StudentDto[].class)
                    .stream().map(s -> {
                        Student std = new Student();
                        std.setFirstName(s.getFirstName());
                        std.setLastName(s.getLastName());
                        std.setEmail(s.getEmail());
                        return std;
                    }).collect(Collectors.toList());
            studentRepository.saveAll(resultData);
            return MessageUtil.getResponseStatus(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtil.getResponseStatus(StatusConstant.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Student>> getStudent(SearchStudentRequest request) {
        try {
            // no paging and only filter in list have been before.
            var students = studentRepository.findAll();
            var finalFilter = buildFinalFilter(request);
            var resultData = students.stream().filter(finalFilter).collect(Collectors.toList());
            if (resultData.size() == 0) {
                return new ResponseEntity<>(new ArrayList<Student>(), HttpStatus.OK);
            }
            return new ResponseEntity<>(resultData, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //private methods
    private Predicate<Student> buildFinalFilter(SearchStudentRequest request) {
        var filters = new ArrayList<Predicate<Student>>();

        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
            filters.add(x -> x.getFirstName().equalsIgnoreCase(request.getFirstName()));
        }

        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
            filters.add(x -> x.getLastName().equalsIgnoreCase(request.getLastName()));
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            filters.add(x -> x.getEmail().equals(request.getEmail()));
        }
        return filters.stream().reduce(x -> true, Predicate::and);
    }
}

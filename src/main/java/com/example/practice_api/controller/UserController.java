package com.example.practice_api.controller;

import com.example.practice_api.dto.UserDto;
import com.example.practice_api.dto.request.BaseSearchRequest;
import com.example.practice_api.dto.request.user.SearchUserRequest;
import com.example.practice_api.dto.response.user.SearchUserResponse;
import com.example.practice_api.entities.User;
import com.example.practice_api.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> signUp(@RequestBody @Valid UserDto user) {
        return userService.signUp(user);
    }

    @PostMapping("/search")
    ResponseEntity<SearchUserResponse> search(@RequestBody @Valid SearchUserRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getSearchRequest().getIndex() - 1, request.getSearchRequest().getSize());
        return userService.search(request, pageRequest);
    }

    @GetMapping("/average-age")
    ResponseEntity<Double> average() {
        return userService.averageAge();
    }

    @GetMapping("/detail/{id}")
    ResponseEntity<UserDto> getUserById(@Valid @PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/group-by-domain")
    ResponseEntity<Map<String, List<User>>> groupByDomain(@RequestBody BaseSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getIndex() - 1, request.getSize());
        return userService.groupByDomainEmail(pageRequest);
    }

    @PutMapping("/inactive")
    ResponseEntity<String> inactiveUser(@RequestBody @Valid List<Integer> ids) {
        return userService.inactiveUser(ids);
    }

    @GetMapping("/all")
    ResponseEntity<List<UserDto>> getAll(@RequestParam @Valid Integer pageIndex, @RequestParam @Valid Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        return userService.all(pageRequest);
    }
}

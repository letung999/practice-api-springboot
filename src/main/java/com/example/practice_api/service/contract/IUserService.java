package com.example.practice_api.service.contract;

import com.example.practice_api.dto.UserDto;
import com.example.practice_api.entities.User;
import com.example.practice_api.dto.request.user.LoginRequest;
import com.example.practice_api.dto.request.user.SearchUserRequest;
import com.example.practice_api.dto.response.user.SearchUserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {
    /**
     * Sign up
     *
     * @param user
     * @return
     */
    ResponseEntity<String> signUp(UserDto user);

    /**
     * login
     *
     * @param loginRequest
     * @return
     */
    ResponseEntity<String> login(LoginRequest loginRequest);

    /**
     * search user by conditions (advance filter)
     *
     * @param request
     * @return
     */
    ResponseEntity<SearchUserResponse> search(SearchUserRequest request, Pageable pageable);

    /**
     * Caculator average age
     *
     * @return
     */
    ResponseEntity<Double> averageAge();

    /**
     * get user by id
     *
     * @param id
     * @return
     */
    ResponseEntity<UserDto> getUserById(Integer id);

    /**
     * group by domain email
     *
     * @return
     */
    ResponseEntity<Map<String, List<User>>> groupByDomainEmail(Pageable pageable);

    /**
     * inactive users by ids
     *
     * @param ids
     * @return
     */
    ResponseEntity<String> inactiveUser(List<Integer> ids);

    /**
     * get all users
     * @param pageable
     * @return
     */
    ResponseEntity<List<UserDto>> all(Pageable pageable);
}

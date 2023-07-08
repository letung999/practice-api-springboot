package com.example.practice_api.service.impl;

import com.example.practice_api.constants.StatusConstant;
import com.example.practice_api.dto.UserDto;
import com.example.practice_api.dto.request.user.LoginRequest;
import com.example.practice_api.dto.request.user.SearchUserRequest;
import com.example.practice_api.dto.response.user.SearchUserResponse;
import com.example.practice_api.entities.User;
import com.example.practice_api.exception.ExistingItemException;
import com.example.practice_api.exception.ResourceNotFoundException;
import com.example.practice_api.mapper.UserMapper;
import com.example.practice_api.repository.UserRepository;
import com.example.practice_api.service.contract.IUserService;
import com.example.practice_api.utils.MessageUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public ResponseEntity<String> signUp(UserDto userRequest) {
        log.info("inside signup{}", userRequest);
        if (validateSignup(userRequest)) {
            var user = userRepository.findByEmail(userRequest.getEmail());
            if (!Objects.isNull(user)) {
                throw new ExistingItemException("user", "email", userRequest.getEmail());
            }
            userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            userRepository.save(userMapper.userDtoToUser(userRequest));
            return MessageUtil.getResponseStatus(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
        }
        return MessageUtil.getResponseStatus(StatusConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        return null;
    }

//    @Override
//    public ResponseEntity<SearchUserResponse> search(SearchUserRequest request, Pageable pageable) {
//        try {
//            var users = userRepository.findAll(pageable);
//            var usersPage = users.getContent();
//            var finalFilter = new ArrayList<Predicate<User>>();
//
//            if (!request.getName().isEmpty()) {
//                finalFilter.add(u -> u.getName().trim().equalsIgnoreCase(request.getName().trim()));
//            }
//
//            if (!request.getAddress().isEmpty()) {
//                finalFilter.add(u -> u.getAddress().trim().equalsIgnoreCase(request.getAddress().trim()));
//            }
//
//            if (!request.getPhoneNumber().isEmpty()) {
//                finalFilter.add(u -> u.getPhoneNumber().trim().equalsIgnoreCase(request.getPhoneNumber().trim()));
//            }
//
//            if (!request.getEmail().isEmpty()) {
//                finalFilter.add(u -> u.getEmail().trim().equalsIgnoreCase(request.getEmail().trim()));
//            }
//
//            if (!request.getGender().isEmpty()) {
//                finalFilter.add(u -> u.getGender().trim().equalsIgnoreCase(request.getGender().trim()));
//            }
//
//            if (request.getAge() != null) {
//                finalFilter.add(u -> u.getAge() == request.getAge());
//            }
//
//            List<User> result = usersPage.stream()
//                    .filter(finalFilter.stream().reduce(x -> true, Predicate::and))
//                    .collect(Collectors.toList());
//
//            var response = new SearchUserResponse();
//            response.setUsers(result);
//            response.setNumberOfPages(users.getTotalPages());
//            response.setNumOfItems(users.getTotalElements());
//            response.setPageIndex(pageable.getPageNumber());
//            response.setPageSize(pageable.getPageSize());
//
//            return result != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<SearchUserResponse> search(SearchUserRequest request, Pageable pageable) {
        try {
            var specification = buildSearchUserFilter(request);
            var users = userRepository.findAll(specification, pageable);
            var resultData = users.getContent();

            var response = new SearchUserResponse();
            response.setUsers(resultData);
            response.setNumberOfPages(users.getTotalPages());
            response.setNumOfItems(users.getTotalElements());
            response.setPageIndex(pageable.getPageNumber());
            response.setPageSize(pageable.getPageSize());

            return resultData != null ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Double> averageAge() {
        try {
            List<User> allUsers = userRepository.findAll();
            Double averageAge = 0.0;
            if (allUsers.isEmpty()) {
                return new ResponseEntity<>(averageAge, HttpStatus.valueOf(StatusConstant.DATA_IS_NOT_FOUND));
            }
            averageAge = allUsers.stream().mapToDouble(u -> u.getAge()).average().getAsDouble();
            return new ResponseEntity<>(averageAge, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Integer id) {

        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        /**
         * if don't use ModelMapper or MapStruct
         */
//            var resultData = user.stream().map(
//                    u -> {
//                        UserDto userDto = new UserDto();
//                        userDto.setId(u.getId());
//                        userDto.setGender(u.getGender());
//                        userDto.setEmail(u.getEmail());
//                        userDto.setName(u.getName());
//                        userDto.setPhoneNumber(u.getPhoneNumber());
//                        userDto.setRole(u.getRole());
//                        userDto.setAddress(u.getAddress());
//                        userDto.setPassword(u.getPassword());
//                        userDto.setStatus(u.getStatus());
//                        return userDto;
//                    }
//            ).findFirst();

        /**
         * use ModelMapper to convert UseEntity to UserDto
         */
//            var resultData = modelMapper.map(user, UserDto.class);

        /**
         * use MapStruct to convert UserEntity to UserDto
         */
        var resultData = userMapper.userToUserDto(user);
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, List<User>>> groupByDomainEmail(Pageable pageable) {
        try {
            var userPage = userRepository.findAll(pageable);
            var users = userPage.getContent();
            if (users.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            var groupDomain = users.stream().collect(Collectors.groupingBy(user -> user.getEmail().substring(user.getEmail().lastIndexOf("."))));
            return new ResponseEntity<>(groupDomain, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * inactive user by ids
     *
     * @param ids
     * @return
     */
    @Override
    public ResponseEntity<String> inactiveUser(List<Integer> ids) {
        try {
            var idsResult = ids.stream().distinct().collect(Collectors.toList());
            var usersInDb = userRepository.findAllById(idsResult);
            if (usersInDb.size() == 0) {
                return MessageUtil.getResponseStatus(StatusConstant.DATA_IS_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            var resultData = usersInDb.stream().filter(x -> !x.getStatus().equalsIgnoreCase("inactive")).collect(Collectors.toList());
            if (resultData.size() != 0) {
                resultData.forEach(u -> u.setStatus("inactive"));
                userRepository.saveAll(resultData);
            }
            return MessageUtil.getResponseStatus(StatusConstant.INF_MSG_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtil.getResponseStatus(StatusConstant.SOME_THING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserDto>> all(Pageable pageable) {
        try {
            var userPage = userRepository.findAll(pageable);
            var resultData = userPage.getContent();
            if (resultData.size() == 0) {
                return new ResponseEntity<>(new ArrayList<UserDto>(), HttpStatus.OK);
            }
            /**
             * don't use ModelMapper or MapStruct
             */
//            var userDtoList = resultData.stream().map(
//                    u -> {
//                        UserDto userDto = new UserDto();
//                        userDto.setId(u.getId());
//                        userDto.setGender(u.getGender());
//                        userDto.setEmail(u.getEmail());
//                        userDto.setName(u.getName());
//                        userDto.setPhoneNumber(u.getPhoneNumber());
//                        userDto.setRole(u.getRole());
//                        userDto.setAddress(u.getAddress());
//                        userDto.setPassword(u.getPassword());
//                        userDto.setStatus(u.getStatus());
//                        return userDto;
//                    }
//            ).collect(Collectors.toList());

            /**
             * use model mapper to convert UserEntity to UserDto
             */
//            var userDtoList = resultData.stream().map(
//                    user -> {
//                        var userToUserDto = modelMapper.map(user, UserDto.class);
//                        return userToUserDto;
//                    }
//            ).collect(Collectors.toList());
//            return new ResponseEntity<>(userDtoList, HttpStatus.OK);

            var userDtoList = userMapper.userListToUserDtoList(resultData);
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //private method

    /**
     * validate use when sign up
     *
     * @param user
     * @return
     */
    private boolean validateSignup(UserDto user) {
        if (!user.getName().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * build Search User Filter use specification
     *
     * @param request
     * @return
     */
    private Specification<User> buildSearchUserFilter(SearchUserRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> finalFilter = new ArrayList<>();

            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }

            if (request.getName() != null && !request.getName().isEmpty()) {
                finalFilter.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }

            if (request.getGender() != null && !request.getGender().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
            }

            if (request.getAddress() != null && !request.getAddress().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("address"), request.getGender()));
            }

            if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("phoneNumber"), request.getPhoneNumber()));
            }

            if (request.getAge() != null) {
                finalFilter.add(criteriaBuilder.equal(root.get("age"), request.getAge()));
            }

            if (request.getSearchRequest().getSortBy() != null && !request.getSearchRequest().getSortBy().isEmpty() && request.getSearchRequest().isAscending()) {
                query.orderBy(criteriaBuilder.asc(root.get(request.getSearchRequest().getSortBy())));
            }

            if (request.getSearchRequest().getSortBy() != null && !request.getSearchRequest().getSortBy().isEmpty() && !request.getSearchRequest().isAscending()) {
                query.orderBy(criteriaBuilder.desc(root.get(request.getSearchRequest().getSortBy())));
            }
            return criteriaBuilder.and(finalFilter.toArray(new Predicate[0]));
        };
    }
}

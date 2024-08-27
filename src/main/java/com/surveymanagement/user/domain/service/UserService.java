package com.surveymanagement.user.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.user.domain.entity.User;

public interface UserService {
    void createUser (User user);
    void updateUser (User user);
    User deleteUser (String userId);
    Optional<User> findUserById(String userId);
    List<User> findAllUser();
}

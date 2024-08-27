package com.surveymanagement.user.application;

import java.util.Optional;

import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;

public class FindUserByIdUseCase {
    private final UserService userService;

    public FindUserByIdUseCase(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> execute(String userId) {
        return userService.findUserById(userId);
    }
}
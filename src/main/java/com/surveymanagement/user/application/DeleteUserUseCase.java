package com.surveymanagement.user.application;

import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;

public class DeleteUserUseCase {
    private final UserService userService;

    public DeleteUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public User execute(String userId) {
        return userService.deleteUser(userId);
    }
}
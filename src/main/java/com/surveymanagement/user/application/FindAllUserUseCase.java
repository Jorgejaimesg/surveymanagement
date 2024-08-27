package com.surveymanagement.user.application;

import java.util.List;

import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;

public class FindAllUserUseCase {
    private final UserService userService;

    public FindAllUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public List<User> execute() {
        return userService.findAllUser();
    }
}
package com.surveymanagement.user.application;

import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;

public class UpdateUserUseCase {
    private final UserService userService;

    public UpdateUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public void execute(User user){
        userService.updateUser(user);
    }
}
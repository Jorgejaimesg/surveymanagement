package com.surveymanagement.userrole.application;

import java.util.Optional;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class FindUserRoleByIdUseCase {
    private final UserRoleService userRoleService;

    public FindUserRoleByIdUseCase(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public Optional<UserRole> execute(String userId ) {
        return userRoleService.findUserRoleById(userId);
    }
}
package com.surveymanagement.userrole.application;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class CreateUserRoleUseCase {
    private final UserRoleService userRoleService;

    public CreateUserRoleUseCase(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public void execute(UserRole userRole) {
        userRoleService.createUserRole(userRole);
    }
}

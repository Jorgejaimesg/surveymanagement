package com.surveymanagement.userrole.application;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class UpdateUserRoleUseCase {
    private final UserRoleService userRoleService;

    public UpdateUserRoleUseCase(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public void execute(UserRole userRole){
        userRoleService.updateUserRole(userRole);
    }
}
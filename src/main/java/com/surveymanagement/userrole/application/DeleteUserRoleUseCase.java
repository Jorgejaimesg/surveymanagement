package com.surveymanagement.userrole.application;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class DeleteUserRoleUseCase {
    private final UserRoleService userRoleService;

    public DeleteUserRoleUseCase(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public UserRole execute(String userId) {
        return userRoleService.deleteUserRole(userId);
    }
}
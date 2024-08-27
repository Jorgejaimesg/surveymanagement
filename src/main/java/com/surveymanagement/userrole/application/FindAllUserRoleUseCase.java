package com.surveymanagement.userrole.application;

import java.util.List;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class FindAllUserRoleUseCase {
    private final UserRoleService userRoleService;

    public FindAllUserRoleUseCase(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public List<UserRole> execute() {
        return userRoleService.findAllUserRole();
    }
}
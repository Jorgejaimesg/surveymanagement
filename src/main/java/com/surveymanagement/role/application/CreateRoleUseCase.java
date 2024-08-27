package com.surveymanagement.role.application;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class CreateRoleUseCase {
    private final RoleService roleService;

    public CreateRoleUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public void execute(Role role) {
        roleService.createRole(role);
    }
}

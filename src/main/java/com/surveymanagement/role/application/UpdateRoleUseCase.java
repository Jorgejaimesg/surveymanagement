package com.surveymanagement.role.application;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class UpdateRoleUseCase {
    private final RoleService roleService;

    public UpdateRoleUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public void execute(Role role){
        roleService.updateRole(role);
    }
}
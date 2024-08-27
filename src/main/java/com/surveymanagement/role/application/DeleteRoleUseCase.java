package com.surveymanagement.role.application;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class DeleteRoleUseCase {
    private final RoleService roleService;

    public DeleteRoleUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public Role execute(String roleName) {
        return roleService.deleteRole(roleName);
    }
}
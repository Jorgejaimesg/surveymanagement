package com.surveymanagement.role.application;

import java.util.Optional;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class FindRoleByIdUseCase {
    private final RoleService roleService;

    public FindRoleByIdUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public Optional<Role> execute(String roleId) {
        return roleService.findRoleById(roleId);
    }
}
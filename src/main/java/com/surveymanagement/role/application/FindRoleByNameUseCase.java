package com.surveymanagement.role.application;

import java.util.Optional;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class FindRoleByNameUseCase {
    private final RoleService roleService;

    public FindRoleByNameUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public Optional<Role> execute(String roleName) {
        return roleService.findRoleByName(roleName);
    }
}
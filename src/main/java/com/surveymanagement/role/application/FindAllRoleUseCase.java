package com.surveymanagement.role.application;

import java.util.List;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class FindAllRoleUseCase {
    private final RoleService roleService;

    public FindAllRoleUseCase(RoleService roleService) {
        this.roleService = roleService;
    }

    public List<Role> execute() {
        return roleService.findAllRole();
    }
}
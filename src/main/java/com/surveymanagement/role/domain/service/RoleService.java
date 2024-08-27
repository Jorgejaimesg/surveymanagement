package com.surveymanagement.role.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.role.domain.entity.Role;

public interface RoleService {
    void createRole (Role role);
    void updateRole (Role role);
    Role deleteRole (String roleName);
    Optional<Role> findRoleByName(String roleName);
    Optional<Role> findRoleById(String roleId);
    List<Role> findAllRole();
}

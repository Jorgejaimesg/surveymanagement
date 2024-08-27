package com.surveymanagement.userrole.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.userrole.domain.entity.UserRole;

public interface UserRoleService {
    void createUserRole (UserRole userRole);
    void updateUserRole (UserRole userRole);
    UserRole deleteUserRole (String userId);
    Optional<UserRole> findUserRoleById(String userId );
    List<UserRole> findAllUserRole();
}

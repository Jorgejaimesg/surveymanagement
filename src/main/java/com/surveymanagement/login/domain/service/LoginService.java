package com.surveymanagement.login.domain.service;

import com.surveymanagement.login.domain.entity.Login;

import java.util.Optional;

public interface LoginService {
    Optional<Login> checkUserRole(String UserName, String Password);

}

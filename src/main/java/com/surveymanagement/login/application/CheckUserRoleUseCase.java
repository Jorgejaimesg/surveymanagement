package com.surveymanagement.login.application;

import java.util.Optional;

import com.surveymanagement.login.domain.entity.Login;
import com.surveymanagement.login.domain.service.LoginService;
public class CheckUserRoleUseCase {


    private final LoginService loginService;

    public CheckUserRoleUseCase(LoginService loginService) {
        this.loginService = loginService;
    }

    public Optional<Login> execute(String Username, String Password) {
        return loginService.checkUserRole(Username,Password);
    }

}

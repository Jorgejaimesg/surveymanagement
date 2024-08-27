package com.surveymanagement;

import com.surveymanagement.login.application.CheckUserRoleUseCase;
import com.surveymanagement.login.domain.service.LoginService;
import com.surveymanagement.login.infrastructure.LoginRepository;
import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;
import com.surveymanagement.user.application.CreateUserUseCase;
import com.surveymanagement.user.domain.service.UserService;
import com.surveymanagement.user.infrastructure.UserRepository;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            startLoginProcess(); 
            // LoginUiController.createAndShowMainUI(); //Just for tests uncomment this line
            // LoginUiController.openUserUi();
        });
    }
   public static void startLoginProcess() {
        LoginService loginService = new LoginRepository();
        CheckUserRoleUseCase checkUserRoleUseCase = new CheckUserRoleUseCase(loginService);
        UserService userService = new UserRepository();
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userService);
        LoginUiController loginUiController = new LoginUiController(createUserUseCase,checkUserRoleUseCase);
        
        loginUiController.frmRegLogin();
    }
}

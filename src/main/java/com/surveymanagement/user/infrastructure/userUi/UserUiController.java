package com.surveymanagement.user.infrastructure.userUi;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;
import com.surveymanagement.user.application.CreateUserUseCase;
import com.surveymanagement.user.application.DeleteUserUseCase;
import com.surveymanagement.user.application.FindAllUserUseCase;
import com.surveymanagement.user.application.FindUserByIdUseCase;
import com.surveymanagement.user.application.UpdateUserUseCase;

public class UserUiController {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByNameUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;

    private JFrame frame;

    



    public UserUiController(CreateUserUseCase createUserUseCase, FindUserByIdUseCase findUserByNameUseCase,
            UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, FindAllUserUseCase findAllUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.findUserByNameUseCase = findUserByNameUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo común para los botones
        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Botón Create User
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateUserUi userUi = new CreateUserUi(createUserUseCase, this);
            userUi.frmRegUser();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateUserUi updateUserUi = new UpdateUserUi(updateUserUseCase, findUserByNameUseCase, this);
            updateUserUi.frmUpdateUser();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllUserUi findAllUserUi = new FindAllUserUi(findAllUserUseCase, this);
            findAllUserUi.showAllUsers();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindUserByIdUi findUserByNameUi = new FindUserByIdUi(findUserByNameUseCase, this);
            findUserByNameUi.showFindUser();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteUserUi deleteCustomerUi = new DeleteUserUi(deleteUserUseCase, this);
            deleteCustomerUi.showDeleteCustomer();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Back to Main Menu", buttonSize, buttonFont);
        btnBackToMain.addActionListener(e -> {
            frame.dispose(); 
            LoginUiController.createAndShowMainUI(); 
        });
        buttonPanel.add(btnBackToMain);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}

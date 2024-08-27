package com.surveymanagement.userrole.infrastructure.userRoleUi;

import javax.swing.*;

import com.surveymanagement.user.application.FindUserByIdUseCase;
import com.surveymanagement.user.domain.service.UserService;
import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.infrastructure.UserRepository;
import com.surveymanagement.userrole.application.DeleteUserRoleUseCase;
import com.surveymanagement.userrole.domain.entity.UserRole;

import java.awt.*;
import java.util.Optional;


public class DeleteUserRoleUi extends JFrame {
    private final DeleteUserRoleUseCase deleteUserRoleUseCase;
    private final UserRoleUiController userRoleUiController;
    private JTextField userIdToDelete; 
    private JTextArea resultArea;
    
    public DeleteUserRoleUi(DeleteUserRoleUseCase deleteUserRoleUseCase, UserRoleUiController userRoleUiController) {
        this.deleteUserRoleUseCase = deleteUserRoleUseCase;
        this.userRoleUiController = userRoleUiController;
    }
    
    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete User Role");
        setSize(500, 500);
        

  
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Delete User Role");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
        
        
        JLabel lblId = new JLabel("User Id:");
        addComponent(lblId, 1, 0);
        
        userIdToDelete = new JTextField();
        addComponent(userIdToDelete, 1, 1);
        

        
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteUserRole());
        addComponent(btnDelete, 2, 0, 2);
        
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
        
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            userRoleUiController.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 2);
    }
    
    private void addComponent(Component component, int row, int col) {
        addComponent(component, row, col, 1);
    }
    
    private void addComponent(Component component, int row, int col, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }
    
    private void deleteUserRole() {
        UserService userService = new UserRepository();
        FindUserByIdUseCase findUserByIdUseCase = new FindUserByIdUseCase(userService);

        String userName = userIdToDelete.getText();

        Optional<User> userOpt = findUserByIdUseCase.execute(userName); //Buscar el nombre del usuario por el id
        User foundUser = userOpt.get();

        UserRole deletedUserRole = deleteUserRoleUseCase.execute(userName);

        if (deletedUserRole != null) {
            String message = String.format(
                "UserRole deleted successfully:\n\n" +
                "Role: %d\n" +
                "Name: %s\n",
                deletedUserRole.getRoleId(),
                foundUser.getUsername()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("UserRole deletion failed. UserRole not found.");
        }
    }
}

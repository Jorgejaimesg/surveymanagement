package com.surveymanagement.user.infrastructure.userUi;

import javax.swing.*;
import java.awt.*;

import com.surveymanagement.user.application.DeleteUserUseCase;
import com.surveymanagement.user.domain.entity.User;


public class DeleteUserUi extends JFrame {
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserUiController userUiController;
    private JTextField jTextField1;
    private JTextArea resultArea;
    
    public DeleteUserUi(DeleteUserUseCase deleteUserUseCase, UserUiController userUiController) {
        this.deleteUserUseCase = deleteUserUseCase;
        this.userUiController = userUiController;
    }
    
    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete User");
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
        
        JLabel titleLabel = new JLabel("Delete User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
        
        JLabel lblId = new JLabel("User Id:");
        addComponent(lblId, 1, 0);
        
        jTextField1 = new JTextField();
        addComponent(jTextField1, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteUser());
        addComponent(btnDelete, 3, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            userUiController.showCrudOptions();
        });
        addComponent(btnClose, 5, 0, 2);
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

    private void deleteUser() {
        String userId = jTextField1.getText();
        User deletedUser = deleteUserUseCase.execute(userId);

        if (deletedUser != null) {
            String message = String.format(
                "User deleted successfully:\n\n" +
                "Id: %d\n" +
                "Active: %b\n" +
                "Username: %s\n"+
                "Password: %s\n",
                deletedUser.getId(),
                deletedUser.isEnabled(),
                deletedUser.getUsername(),
                deletedUser.getPassword()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("User deletion failed.");
        }
    }
}

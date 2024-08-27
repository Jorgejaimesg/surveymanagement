package com.surveymanagement.user.infrastructure.userUi;

import java.util.Optional;

import javax.swing.*;
import java.awt.*;


import com.surveymanagement.user.application.FindUserByIdUseCase;
import com.surveymanagement.user.domain.entity.User;

public class FindUserByIdUi extends JFrame {
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UserUiController userUiController;
    private JTextField jTextField1;
    private JTextArea resultArea;



    public FindUserByIdUi(FindUserByIdUseCase findUserByIdUseCase, UserUiController userUiController) {
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.userUiController = userUiController;
    }

    public void showFindUser() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find User");
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

        JLabel titleLabel = new JLabel("Find User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("User to find:");
        addComponent(lblId, 1, 0);

        jTextField1 = new JTextField();
        addComponent(jTextField1, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findUser());
        addComponent(btnFind, 2, 0, 2);

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
        btnClose.addActionListener(e -> {  dispose();
            userUiController.showCrudOptions();
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

    private void findUser() {
        String userId = jTextField1.getText();
        Optional<User> userOpt = findUserByIdUseCase.execute(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String message = String.format(
                "User found successfully:\n\n" +
                "Id: %d\n" +
                "Active: %b\n" +
                "Username: %s\n"+
                "Password: %s\n",
                user.getId(),
                user.isEnabled(),
                user.getUsername(),
                user.getPassword()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("User not found!");
        }
    }
}

package com.surveymanagement.userrole.infrastructure.userRoleUi;

import java.util.Optional;

import javax.swing.*;

import com.surveymanagement.user.application.FindUserByIdUseCase;
import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;
import com.surveymanagement.user.infrastructure.UserRepository;
import com.surveymanagement.userrole.application.FindUserRoleByIdUseCase;
import com.surveymanagement.userrole.domain.entity.UserRole;

import java.awt.*;

public class FindUserRoleByNameUi extends JFrame {
    private final FindUserRoleByIdUseCase findUserRoleByIdUseCase;
    private final UserRoleUiController userRoleUiController;
    private JTextField userIdToFind; 
    private JTextArea resultArea;



    public FindUserRoleByNameUi(FindUserRoleByIdUseCase findUserRoleByIdUseCase, UserRoleUiController userRoleUiController) {
        this.findUserRoleByIdUseCase = findUserRoleByIdUseCase;
        this.userRoleUiController = userRoleUiController;
    }

    public void showFindUserRole() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find User Role");
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

        JLabel titleLabel = new JLabel("Find User Role");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("User Id to find:");
        addComponent(lblId, 1, 0);

        userIdToFind = new JTextField();
        addComponent(userIdToFind, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findUserRole());
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

    private void findUserRole() {
        UserService userService = new UserRepository();
        FindUserByIdUseCase findUserByIdUseCase = new FindUserByIdUseCase(userService);

        String userName = userIdToFind.getText();

        Optional<User> userOpt = findUserByIdUseCase.execute(userName); //Buscar el nombre del usuario por el id
        User foundUser = userOpt.get();
        
        Optional<UserRole> userRoleOpt = findUserRoleByIdUseCase.execute(userName);
        if (userRoleOpt.isPresent()) {
            UserRole userRole = userRoleOpt.get();
            String message = String.format(
                "User found:\n\n" +
                "Role Id: %s\n" +
                "Username: %s\n",
                userRole.getRoleId(),
                foundUser.getUsername()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("UserRole not found!");
        }
    }
}

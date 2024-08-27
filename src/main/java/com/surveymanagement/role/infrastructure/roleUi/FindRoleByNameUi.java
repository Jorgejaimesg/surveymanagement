package com.surveymanagement.role.infrastructure.roleUi;

import java.util.Optional;
import java.util.List;

import javax.swing.*;
import java.awt.*;


import com.surveymanagement.role.application.FindAllRoleUseCase;
import com.surveymanagement.role.application.FindRoleByNameUseCase;
import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;
import com.surveymanagement.role.infrastructure.RoleRepository;

public class FindRoleByNameUi extends JFrame {
    private final FindRoleByNameUseCase findRoleByNameUseCase;
    private final RoleUiController roleUiController;
    private JComboBox<String> roleOptions; 
    private JTextArea resultArea;



    public FindRoleByNameUi(FindRoleByNameUseCase findRoleByNameUseCase, RoleUiController roleUiController) {
        this.findRoleByNameUseCase = findRoleByNameUseCase;
        this.roleUiController = roleUiController;
    }

    public void showFindRole() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Role");
        setSize(500, 500);

        RoleService roleService = new RoleRepository();
        FindAllRoleUseCase findAllRoleUseCase = new FindAllRoleUseCase(roleService);
        initComponents(findAllRoleUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindAllRoleUseCase findAllRoleUseCase) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Role");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Role to find:");
        addComponent(lblId, 1, 0);

        roleOptions = new JComboBox<>();
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName());
        }
        addComponent(roleOptions, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findRole());
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
            roleUiController.showCrudOptions();
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

    private void findRole() {
        String roleName = roleOptions.getSelectedItem().toString();
        Optional<Role> roleOpt = findRoleByNameUseCase.execute(roleName);
        if (roleOpt.isPresent()) {
            Role role = roleOpt.get();
            String message = String.format(
                "Role found:\n\n" +
                "Role Id: %s\n" +
                "Role Name: %s\n",
                role.getId(),
                role.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Role not found!");
        }
    }
}

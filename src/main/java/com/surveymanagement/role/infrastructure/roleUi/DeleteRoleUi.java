package com.surveymanagement.role.infrastructure.roleUi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.surveymanagement.role.application.DeleteRoleUseCase;
import com.surveymanagement.role.application.FindAllRoleUseCase;
import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.infrastructure.RoleRepository;
import com.surveymanagement.role.domain.service.*;


public class DeleteRoleUi extends JFrame {
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final RoleUiController roleUiController;
    private JComboBox<String> roleOptions; 
    private JTextArea resultArea;
    
    public DeleteRoleUi(DeleteRoleUseCase deleteRoleUseCase, RoleUiController roleUiController) {
        this.deleteRoleUseCase = deleteRoleUseCase;
        this.roleUiController = roleUiController;
    }
    
    public void showDeleteRole() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Role");
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
        
        JLabel titleLabel = new JLabel("Delete Role");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
        
        JLabel lblId = new JLabel("Role name:");
        addComponent(lblId, 1, 0);
        
        roleOptions = new JComboBox<>();
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName());
        }
        addComponent(roleOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteRole());
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

    private void deleteRole() {
        String roleName = (String) roleOptions.getSelectedItem();
        Role deletedRole = deleteRoleUseCase.execute(roleName);

        if (deletedRole != null) {
            String message = String.format(
                "Role deleted successfully:\n\n" +
                "Role Id: %d\n" +
                "Name: %s\n",
                deletedRole.getId(),
                deletedRole.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Role deletion failed. Role not found.");
        }
    }
}

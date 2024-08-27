package com.surveymanagement.userrole.infrastructure.userRoleUi;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.role.application.FindAllRoleUseCase;
import com.surveymanagement.role.application.FindRoleByNameUseCase;
import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;
import com.surveymanagement.role.infrastructure.RoleRepository;
import com.surveymanagement.userrole.application.CreateUserRoleUseCase;
import com.surveymanagement.userrole.domain.entity.UserRole;

public class CreateUserRoleUi extends JFrame {
    private final CreateUserRoleUseCase createUserRoleUseCase;
    private final UserRoleUiController userRoleUiController; 

    private JTextField userIdBox;
    private JComboBox<String> roleOptions;
    
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateUserRoleUi(CreateUserRoleUseCase createUserRoleUseCase, UserRoleUiController userRoleUiController) { 
        this.createUserRoleUseCase = createUserRoleUseCase;
        this.userRoleUiController = userRoleUiController; 
    }

    public void frmRegUserRole() {
        RoleService roleService = new RoleRepository();
        FindAllRoleUseCase findAllRoleUseCase = new FindAllRoleUseCase(roleService);
        FindRoleByNameUseCase findRoleByNameUseCase = new FindRoleByNameUseCase(roleService);

        
        initComponents(findAllRoleUseCase,findRoleByNameUseCase);
        setVisible(true);
    }

    private void initComponents(FindAllRoleUseCase findAllRoleUseCase,FindRoleByNameUseCase findRoleByNameUseCase) {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Assign Role");
        setSize(500, 500);
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Create UserRole");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        userIdBox = new JTextField();

        roleOptions = new JComboBox<>();
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName());
            
        }
        
        addComponent(roleOptions, 1, 1);
        
        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        
        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveUserRole(findRoleByNameUseCase));
        jButton3.addActionListener(e -> {
            dispose();
            userRoleUiController.showCrudOptions(); 
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("User Id:"), 1, 0);
        addComponent(userIdBox, 1, 1);
        addComponent(new JLabel("Role:"), 2, 0);
        addComponent(roleOptions, 2, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);
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
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        add(component, gbc);
    }

    private void saveUserRole(FindRoleByNameUseCase findRoleByNameUseCase) {
        String roleName = roleOptions.getSelectedItem().toString();
        Optional<Role> roleOpt = findRoleByNameUseCase.execute(roleName);
        Role foundRole = roleOpt.get();
        
        try {
            UserRole userRole = new UserRole();
            userRole.setRoleId(foundRole.getId());
            userRole.setUserId(Integer.parseInt(userIdBox.getText()));


            createUserRoleUseCase.execute(userRole); // Corrected from "customer" to "userRole"
            JOptionPane.showMessageDialog(this, "UserRole added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        userIdBox.setText("");

    }
}

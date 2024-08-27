package com.surveymanagement.userrole.infrastructure.userRoleUi;

import java.awt.*;
import java.util.Optional;
import java.util.List;
import javax.swing.*;

import com.surveymanagement.userrole.application.UpdateUserRoleUseCase;
import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;
import com.surveymanagement.role.infrastructure.RoleRepository;
import com.surveymanagement.role.application.FindAllRoleUseCase;
import com.surveymanagement.role.application.FindRoleByIdUseCase;
import com.surveymanagement.role.application.FindRoleByNameUseCase;
import com.surveymanagement.userrole.application.FindUserRoleByIdUseCase;
import com.surveymanagement.userrole.domain.entity.UserRole;

public class UpdateUserRoleUi extends JFrame {
    private final UpdateUserRoleUseCase updateUserRoleUseCase;
    private final FindUserRoleByIdUseCase findUserRoleByIdUseCase;
    private final UserRoleUiController userRoleUiController;

    private JTextField jTextField2; // UserRole Name
    private JComboBox<String> roleOptions;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find


    public UpdateUserRoleUi(UpdateUserRoleUseCase updateUserRoleUseCase, FindUserRoleByIdUseCase findUserRoleByIdUseCase, UserRoleUiController userRoleUiController) {
        this.updateUserRoleUseCase = updateUserRoleUseCase;
        this.findUserRoleByIdUseCase = findUserRoleByIdUseCase;
        this.userRoleUiController = userRoleUiController;
    }

    public void frmUpdateUserRole() {
        RoleService RoleService = new RoleRepository();
        FindAllRoleUseCase findAllRoleUseCase = new FindAllRoleUseCase(RoleService);
        initComponents(findAllRoleUseCase);
        setVisible(true);
    }

    private void initComponents(FindAllRoleUseCase findAllRoleUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update User Role");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update Assign Role");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        jTextField2 = new JTextField();
        
       roleOptions = new JComboBox<>();
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName());
        }


        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateUserRole());
        jButton3.addActionListener(e -> {
            dispose();
            userRoleUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findUserRole());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("User Id:"), 1, 0);
        addComponent(jTextField2, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Role:"), 3, 0);
        addComponent(roleOptions, 3, 1);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 4, 0, 2);

        setLocationRelativeTo(null);

        hideComponents();
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
        gbc.anchor = GridBagConstraints.CENTER; 

        add(component, gbc); 
    }

    private void updateUserRole() {
        RoleService roleService = new RoleRepository();
        FindRoleByNameUseCase findRoleByNameUseCase = new FindRoleByNameUseCase(roleService);
        try {
            UserRole userRole = new UserRole();
            userRole.setUserId(Integer.parseInt(jTextField2.getText()));

            String selectedRoleName = (String) roleOptions.getSelectedItem();
            Optional<Role> selectedRole = findRoleByNameUseCase.execute(selectedRoleName);
    	    
            userRole.setRoleId(selectedRole.get().getId());
            
            updateUserRoleUseCase.execute(userRole);
            JOptionPane.showMessageDialog(this, "UserRole updated successfully!");

            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findUserRole() {
        RoleService roleService = new RoleRepository();
        FindRoleByIdUseCase findRoleByIdUseCase = new FindRoleByIdUseCase(roleService);
    
        String userId = jTextField2.getText();
        Optional<UserRole> userRoleToUpdate = findUserRoleByIdUseCase.execute(userId);
    
        if (userRoleToUpdate.isPresent()) {
            UserRole foundUserRole = userRoleToUpdate.get();
    
            jTextField2.setEditable(false);
    
            
            String roleId = String.valueOf(foundUserRole.getRoleId());
    
            
            Optional<Role> roleSearch = findRoleByIdUseCase.execute(roleId);
            
            if (roleSearch.isPresent()) {
                roleOptions.setSelectedItem(roleSearch.get().getName());
            } else {
                JOptionPane.showMessageDialog(this, "Role not found for the given roleId!", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            showComponents();
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "UserRole not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void resetFields() {
        jTextField2.setText("");
        jTextField2.setEditable(true);
        hideComponents();
    }

    private void hideComponents() {
        roleOptions.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        roleOptions.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }
    
}

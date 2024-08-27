package com.surveymanagement.role.infrastructure.roleUi;

import java.awt.*;
import java.util.Optional;
import java.util.List;
import javax.swing.*;

import com.surveymanagement.role.application.UpdateRoleUseCase;
import com.surveymanagement.role.application.FindAllRoleUseCase;
import com.surveymanagement.role.application.FindRoleByNameUseCase;
import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;
import com.surveymanagement.role.infrastructure.RoleRepository;

public class UpdateRoleUi extends JFrame {
    private final UpdateRoleUseCase updateRoleUseCase;
    private final FindRoleByNameUseCase findRoleByNameUseCase;
    private final RoleUiController roleUiController;

    private JComboBox<String> roleOptions;
    private JTextField jTextField2; // Role Name
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int foundId;

    public UpdateRoleUi(UpdateRoleUseCase updateRoleUseCase, FindRoleByNameUseCase findRoleByNameUseCase, RoleUiController roleUiController) {
        this.updateRoleUseCase = updateRoleUseCase;
        this.findRoleByNameUseCase = findRoleByNameUseCase;
        this.roleUiController = roleUiController;
    }

    public void frmUpdateRole() {
        RoleService roleService = new RoleRepository();
        FindAllRoleUseCase findAllRoleUseCase = new FindAllRoleUseCase(roleService);
        initComponents(findAllRoleUseCase);
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents(FindAllRoleUseCase findAllRoleUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Role");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update Role");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        roleOptions = new JComboBox<>();
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName());
        }

        jTextField2 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateRole());
        jButton3.addActionListener(e -> {
            dispose();
            roleUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findRole());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Role:"), 1, 0);
        addComponent(roleOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Role Name:"), 3, 0);
        addComponent(jTextField2, 3, 1);

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
        gbc.gridx = col;  // La columna en la que se agregará el componente
        gbc.gridy = row;  // La fila en la que se agregará el componente
        gbc.gridwidth = width;  // Número de celdas de ancho que ocupará el componente
        gbc.fill = GridBagConstraints.HORIZONTAL;  // El componente se estirará horizontalmente
        gbc.insets = new Insets(5, 5, 5, 5);  // Márgenes alrededor del componente
        gbc.anchor = GridBagConstraints.CENTER; // Centro del componente

        add(component, gbc);  // Añade el componente con las restricciones especificadas
    }

    private void updateRole() {
        try {
            Role role = new Role();
            role.setId(foundId);
            role.setName(jTextField2.getText());
    
            updateRoleUseCase.execute(role);
            JOptionPane.showMessageDialog(this, "Role updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            // Imprime el mensaje de error completo en la consola
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findRole() {
        String codeToUpdate = roleOptions.getSelectedItem().toString();
        Optional<Role> roleToUpdate = findRoleByNameUseCase.execute(codeToUpdate);
  
        if (roleToUpdate.isPresent()) {
            Role foundRole = roleToUpdate.get();
            foundId = foundRole.getId();
            jTextField2.setText(foundRole.getName());
            roleOptions.setEditable(false);
            showComponents();
            revalidate(); // Asegura que el layout se actualice
            repaint(); // Redibuja la ventana
        } else {
            JOptionPane.showMessageDialog(this, "Role not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField2.setText("");
        roleOptions.setEditable(true);
        hideComponents();
    }

    private void hideComponents() {
        jTextField2.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        jTextField2.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        roleOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        RoleService roleService = new RoleRepository();
        FindAllRoleUseCase findAllRoleUseCase = new FindAllRoleUseCase(roleService);
        
        List<Role> roles = findAllRoleUseCase.execute();
        for (Role role : roles) {
            roleOptions.addItem(role.getName()); // Añade los roles actualizados al JComboBox
        }
    }

    
}

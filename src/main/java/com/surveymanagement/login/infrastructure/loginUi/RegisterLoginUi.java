package com.surveymanagement.login.infrastructure.loginUi;

import com.surveymanagement.user.application.CreateUserUseCase;
import com.surveymanagement.user.domain.entity.User;

import java.awt.*;
import javax.swing.*;

public class RegisterLoginUi extends JFrame {
    private final CreateUserUseCase createUserUseCase;
    private final LoginUiController loginUiController; 

    private boolean activeUser;
    private JTextField jTextField1; // Username
    private JTextField jTextField2; // Password
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public RegisterLoginUi(CreateUserUseCase createUserUseCase, LoginUiController loginUiController) { 
        this.createUserUseCase = createUserUseCase;
        this.loginUiController = loginUiController;
        initComponents(); // Añadido para inicializar componentes dentro del constructor
        setVisible(true);
    }

    public static void frmRegUser(CreateUserUseCase createUserUseCase, LoginUiController loginUiController) {
        SwingUtilities.invokeLater(() -> new RegisterLoginUi(createUserUseCase, loginUiController));
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/survey.png")).getImage());
        setTitle("Create User");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create User");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField1 = new JTextField();
        jTextField2 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveUser());
        jButton3.addActionListener(e -> {
            dispose(); // Cierra la ventana de registro
            loginUiController.setVisible(true); // Vuelve a mostrar la ventana de login
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("UserName:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(new JLabel("Password:"), 2, 0);
        addComponent(jTextField2, 2, 1);

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

    private void saveUser() {
        activeUser = true;
        try {
            User user = new User();
            user.setEnabled(activeUser);
            user.setUsername(jTextField1.getText());
            user.setPassword(jTextField2.getText());

            createUserUseCase.execute(user);
            JOptionPane.showMessageDialog(this, "User added successfully!");
            resetFields();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField1.setText("");
        jTextField2.setText("");
    }
}

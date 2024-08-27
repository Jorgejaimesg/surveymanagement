package com.surveymanagement.user.infrastructure.userUi;

import java.awt.*;
import java.util.Optional;
import javax.swing.*;

import com.surveymanagement.user.application.UpdateUserUseCase;
import com.surveymanagement.user.application.FindAllUserUseCase;
import com.surveymanagement.user.application.FindUserByIdUseCase;
import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;
import com.surveymanagement.user.infrastructure.UserRepository;

public class UpdateUserUi extends JFrame {
    private final UpdateUserUseCase updateUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UserUiController userUiController;

    
    private JTextField jTextField1; //idb
    private JCheckBox isEnabledCheck;
    private JTextField jTextField2; // User Name
    private JTextField jTextField3; // password

    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    
    public UpdateUserUi(UpdateUserUseCase updateUserUseCase, FindUserByIdUseCase findUserByIdUseCase, UserUiController userUiController) {
        this.updateUserUseCase = updateUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.userUiController = userUiController;
    }

    public void frmUpdateUser() {
        UserService userService = new UserRepository();
        FindAllUserUseCase findAllUserUseCase = new FindAllUserUseCase(userService);
        initComponents(findAllUserUseCase);

        setVisible(true);
    }

    private void initComponents(FindAllUserUseCase findAllUserUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update User");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update User");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        

        jTextField1 = new JTextField();
        isEnabledCheck = new JCheckBox();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateUser());
        jButton3.addActionListener(e -> {
            dispose();
            userUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findUser());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("User Id:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Active User:"), 3, 0);
        addComponent(isEnabledCheck, 3, 1);
        addComponent(new JLabel("Username:"), 4, 0);
        addComponent(jTextField2, 4, 1);
        addComponent(new JLabel("Password:"), 5, 0);
        addComponent(jTextField3, 5, 1);



        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 6, 0, 2);

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

    private void updateUser() {
        
        try {
            User user = new User();
            user.setId(Integer.parseInt(jTextField1.getText()));
            user.setEnabled(isEnabledCheck.isSelected());
            user.setUsername(jTextField2.getText());
            user.setPassword(jTextField3.getText());
    
            updateUserUseCase.execute(user);
            JOptionPane.showMessageDialog(this, "User updated successfully!");
      
            resetFields();
        } catch (Exception ex) {
            // Imprime el mensaje de error completo en la consola
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findUser() {
        String codeToUpdate = jTextField1.getText();
        Optional<User> userToUpdate = findUserByIdUseCase.execute(codeToUpdate);
  
        if (userToUpdate.isPresent()) {
            User foundUser = userToUpdate.get();
            isEnabledCheck.setSelected(foundUser.isEnabled());
            jTextField2.setText(foundUser.getUsername());
            jTextField3.setText(foundUser.getPassword());
            jTextField1.setEditable(false);
            showComponents();
            revalidate(); // Asegura que el layout se actualice
            repaint(); // Redibuja la ventana
        } else {
            JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        isEnabledCheck.setSelected(false);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField1.setEditable(true);
        hideComponents();
    }

    private void hideComponents() {
        isEnabledCheck.setVisible(false);
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        revalidate();
        repaint();
    }

    private void showComponents() {
        isEnabledCheck.setVisible(true);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
       
    }


    
}

package com.surveymanagement.role.infrastructure.roleUi;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;
import com.surveymanagement.role.application.CreateRoleUseCase;
import com.surveymanagement.role.application.DeleteRoleUseCase;
import com.surveymanagement.role.application.FindRoleByNameUseCase;
import com.surveymanagement.role.application.UpdateRoleUseCase;

public class RoleUiController {
    private final CreateRoleUseCase createRoleUseCase;
    private final FindRoleByNameUseCase findRoleByNameUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private JFrame frame;

    



    public RoleUiController(CreateRoleUseCase createRoleUseCase, FindRoleByNameUseCase findRoleByNameUseCase,
            UpdateRoleUseCase updateRoleUseCase, DeleteRoleUseCase deleteRoleUseCase) {
        this.createRoleUseCase = createRoleUseCase;
        this.findRoleByNameUseCase = findRoleByNameUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
        this.deleteRoleUseCase = deleteRoleUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Roles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Roles");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo común para los botones
        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Botón Create Role
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateRoleUi roleUi = new CreateRoleUi(createRoleUseCase, this);
            roleUi.frmRegRole();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateRoleUi updateRoleUi = new UpdateRoleUi(updateRoleUseCase, findRoleByNameUseCase, this);
            updateRoleUi.frmUpdateRole();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindRoleByNameUi findRoleByNameUi = new FindRoleByNameUi(findRoleByNameUseCase, this);
            findRoleByNameUi.showFindRole();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteRoleUi deleteRoleUi = new DeleteRoleUi(deleteRoleUseCase, this);
            deleteRoleUi.showDeleteRole();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Back to Main Menu", buttonSize, buttonFont);
        btnBackToMain.addActionListener(e -> {
            frame.dispose(); 
            LoginUiController.createAndShowMainUI(); 
        });
        buttonPanel.add(btnBackToMain);

        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}

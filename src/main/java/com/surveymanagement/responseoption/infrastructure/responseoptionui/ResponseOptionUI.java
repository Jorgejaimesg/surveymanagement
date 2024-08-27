package com.surveymanagement.responseoption.infrastructure.responseoptionui;

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
import com.surveymanagement.responseoption.application.CreateResponseOptionUseCase;
import com.surveymanagement.responseoption.application.DeleteResponseOptionUseCase;
import com.surveymanagement.responseoption.application.FindResponseOptionByIdUseCase;
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.application.UpdateResponseOptionUseCase;


public class ResponseOptionUI {

    private final CreateResponseOptionUseCase createResponseOptionUseCase;
    private final DeleteResponseOptionUseCase deleteResponseOptionUseCase;
    private final FindResponseOptionByIdUseCase findResponseOptionByIdUseCase;
    private final UpdateResponseOptionUseCase updateResponseOptionUseCase;
    private JFrame frame;

    public ResponseOptionUI(
            CreateResponseOptionUseCase createResponseOptionUseCase,
            DeleteResponseOptionUseCase deleteResponseOptionUseCase,
            FindResponseOptionByIdUseCase findResponseOptionByIdUseCase,
            UpdateResponseOptionUseCase updateResponseOptionUseCase) {
        this.createResponseOptionUseCase = createResponseOptionUseCase;
        this.deleteResponseOptionUseCase = deleteResponseOptionUseCase;
        this.findResponseOptionByIdUseCase = findResponseOptionByIdUseCase;
        this.updateResponseOptionUseCase = updateResponseOptionUseCase;
    }

    public void showCrudOptions(){

        frame = new JFrame("ResponseOption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

                // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("ResponseOption");
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

        // Botón Create ResponseOption
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            AddResponseOptionUI addresponseOptionUi = new AddResponseOptionUI(createResponseOptionUseCase, this);
            addresponseOptionUi.frmRegResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateResponseOptionUI updateResponseOptionUi = new UpdateResponseOptionUI(updateResponseOptionUseCase, findResponseOptionByIdUseCase, this);
            updateResponseOptionUi.frmUpdateResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindResponseOptionUI findResponseOptionUI = new FindResponseOptionUI(findResponseOptionByIdUseCase, this);
            findResponseOptionUI.showFindResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteResponseOptionUI deleteCustomerUI = new DeleteResponseOptionUI(deleteResponseOptionUseCase, this);
            deleteCustomerUI.showDeleteResponseOption();
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
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));



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

package com.surveymanagement.survey.infrastructure.surveyui;

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
import com.surveymanagement.survey.application.CreateSurveyUseCase;
import com.surveymanagement.survey.application.DeleteSurveyUseCase;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.application.UpdateSurveyUseCase;


public class SurveyUI {

private final CreateSurveyUseCase createSurveyUseCase;
    private final DeleteSurveyUseCase deleteSurveyUseCase;
    private final FindAllSurveyUseCase findAllSurveyUseCase;
    private final FindSurveyByCodeUseCase findSurveyByCodeUseCase;
    private final UpdateSurveyUseCase updateSurveyUseCase;
    private JFrame frame;

    public SurveyUI(
            CreateSurveyUseCase createSurveyUseCase,
            DeleteSurveyUseCase deleteSurveyUseCase,
            FindAllSurveyUseCase findAllSurveyUseCase,
            FindSurveyByCodeUseCase findSurveyByCodeUseCase,
            UpdateSurveyUseCase updateSurveyUseCase) {
        this.createSurveyUseCase = createSurveyUseCase;
        this.deleteSurveyUseCase = deleteSurveyUseCase;
        this.findAllSurveyUseCase = findAllSurveyUseCase;
        this.findSurveyByCodeUseCase = findSurveyByCodeUseCase;
        this.updateSurveyUseCase = updateSurveyUseCase;
    }

    public void showCrudOptions(){
        frame = new JFrame("Surveys");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
    
                // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Surveys");
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

        // Botón Create Survey
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            AddSurveyUI addsurveyUi = new AddSurveyUI(createSurveyUseCase, this);
            addsurveyUi.frmRegSurvey();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateSurveyUI updateSurveyUi = new UpdateSurveyUI(updateSurveyUseCase, findSurveyByCodeUseCase, this);
            updateSurveyUi.frmUpdateSurvey();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindSurveyUI findSurveyUI = new FindSurveyUI(findSurveyByCodeUseCase, this);
            findSurveyUI.showFindSurvey();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteSurveyUI deleteCustomerUI = new DeleteSurveyUI(deleteSurveyUseCase, this);
            deleteCustomerUI.showDeleteSurvey();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllSurveyUI findAllSurveyUi = new FindAllSurveyUI(findAllSurveyUseCase, this);
            findAllSurveyUi.showAllSurveys();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
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

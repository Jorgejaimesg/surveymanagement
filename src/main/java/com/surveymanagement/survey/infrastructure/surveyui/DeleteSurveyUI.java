package com.surveymanagement.survey.infrastructure.surveyui;

import java.awt.*;
import java.util.List;
import java.util.Optional;

import javax.swing.*;

import com.surveymanagement.survey.application.DeleteSurveyUseCase;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class DeleteSurveyUI extends JFrame{
    private final DeleteSurveyUseCase deleteSurveyUseCase;
    private final SurveyUI surveyui;

    public DeleteSurveyUI(DeleteSurveyUseCase deleteSurveyUseCase,
            SurveyUI surveyui) {
        this.deleteSurveyUseCase = deleteSurveyUseCase;
        this.surveyui = surveyui;
    }

    private JComboBox<String> surveyBox;
    private JTextArea resultArea;
    
    public void showDeleteSurvey(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Survey");
        setSize(500, 500);
        
        SurveyService roleService = new SurveyRepository();
        FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(roleService);
        initComponents(findAllSurveyUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindAllSurveyUseCase findAllSurveyUseCase){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Survey");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
    
        JLabel labelCategory = new JLabel("Catalog Category:");
        addComponent(labelCategory, 1, 0, 1);

        surveyBox = new JComboBox<>();
        List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
        for (Survey survey : surveys) {
            surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
        }
        addComponent(surveyBox, 1, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSurvey());
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
            surveyui.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 2);
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



        private void deleteSurvey() {
        int surveyCode = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
        SurveyService surveyService = new SurveyRepository();
        FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
        Optional<Survey> foundSurvey = findSurveyByCodeUseCase.findSurveyByCode(surveyCode);
        
        if (foundSurvey.isPresent()) {
            deleteSurveyUseCase.execute(foundSurvey.get().getId());
            String message = String.format(
                "Survey deleted successfully:\n\n" +
                "ID: %d\n" +
                "Name: %s\n" +
                "Description %s\n.",
                foundSurvey.get().getId(),
                foundSurvey.get().getDescription(),
                foundSurvey.get().getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Survey deletion failed. Survey not found.");
        }
    }

    private String TextBeforeDot(String text) {
        // Buscar la posición del primer punto en la cadena
        int position = text.indexOf('.');
        if (position != -1) {
            return text.substring(0, position);
        } else {
            return text;
        }
    }
}
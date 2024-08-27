package com.surveymanagement.survey.infrastructure.surveyui;

import java.awt.*;
import javax.swing.*;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class FindSurveyUI extends JFrame {
    private final FindSurveyByCodeUseCase findSurveyByCodeUseCase;
    private final SurveyUI surveyUI;
    public FindSurveyUI(FindSurveyByCodeUseCase findSurveyByCodeUseCase,
            SurveyUI surveyUI) {
        this.findSurveyByCodeUseCase = findSurveyByCodeUseCase;
        this.surveyUI = surveyUI;
    }
    
    private JComboBox<String> surveyOptions;
    private JTextArea resultArea;

    public void showFindSurvey() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Survey");
        setSize(500, 500);

        SurveyService surveyService = new SurveyRepository();
        FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
        initComponents(findAllSurveyUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindAllSurveyUseCase findAllSurveyUseCase) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Survey");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Survey to find:");
        addComponent(lblId, 1, 0, 1);

        surveyOptions = new JComboBox<>();
        List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
        for (Survey survey : surveys) {
            surveyOptions.addItem(String.valueOf(survey.getId())+ ". " + survey.getName());
        }
        addComponent(surveyOptions, 1, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findSurvey());
        addComponent(btnFind, 2, 0, 2);

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
        btnClose.addActionListener(e -> {  dispose();
            surveyUI.showCrudOptions();
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
        private void findSurvey() {
        int surveyId =Integer.parseInt(TextBeforeDot(surveyOptions.getSelectedItem().toString()));
        Optional<Survey> surveyOpt = findSurveyByCodeUseCase.findSurveyByCode(surveyId);
        if (surveyOpt.isPresent()) {
            Survey survey = surveyOpt.get();
            String message = String.format(
                "Survey found:\n\n" +
                "ID: %s\n" +
                "Survey Name: %s\n" +
                "Survey Description: %s\n",
                survey.getId(),
                survey.getName(),
                survey.getDescription()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Survey not found!");
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


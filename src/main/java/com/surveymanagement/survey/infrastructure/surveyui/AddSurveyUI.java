package com.surveymanagement.survey.infrastructure.surveyui;

import java.awt.*;

import javax.swing.*;

import com.surveymanagement.survey.application.CreateSurveyUseCase;
import com.surveymanagement.survey.domain.entity.Survey;

public class AddSurveyUI extends JFrame {
    private final CreateSurveyUseCase createSurveyUseCase;
    private final SurveyUI surveyUI;

    public AddSurveyUI(
            CreateSurveyUseCase createSurveyUseCase,
            SurveyUI surveyUI) {
        this.createSurveyUseCase = createSurveyUseCase;
        this.surveyUI = surveyUI;
    }
    private JTextField name, description;
    private JButton create, goback, reset ;
    
    public void frmRegSurvey(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Survey");
        setSize(500, 500);

        JLabel title = new JLabel("Create Survey");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        name = new JTextField();
        description = new JTextField();

        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoySurvey());
        goback.addActionListener(e -> {
            dispose();
            surveyUI.showCrudOptions(); 
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(title, 0, 0, 2);
        addComponent(new JLabel("Survey Name:"), 1, 0, 1);
        addComponent(name, 1, 1, 1);
        addComponent(new JLabel("Survey Description:"), 2, 0, 1);
        addComponent(description, 2, 1, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(reset);
        buttonPanel.add(goback);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);

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

    private void saveCategoySurvey() {
        try {
            Survey survey = new Survey();
            survey.setName(name.getText().trim());
            survey.setDescription(description.getText().trim());


            createSurveyUseCase.execute(survey); // Corrected from "customer" to "role"
            JOptionPane.showMessageDialog(this, "Survey added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        name.setText("");
        description.setText("");
    }
}

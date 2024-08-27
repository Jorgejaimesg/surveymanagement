package com.surveymanagement.survey.infrastructure.surveyui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.domain.entity.Survey;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;

public class FindAllSurveyUI {
    private final FindAllSurveyUseCase findAllSurveyUseCase;
        private final SurveyUI surveyui;
        private JFrame frame;
    
        public FindAllSurveyUI(FindAllSurveyUseCase findAllSurveyUseCase, SurveyUI surveyUI) {
            this.findAllSurveyUseCase = findAllSurveyUseCase;
            this.surveyui = surveyUI;
        }
    
        public void showAllSurveys() {
            frame = new JFrame("All Surveys");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
    
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            JLabel titleLabel = new JLabel("All Surveys");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(titleLabel, BorderLayout.NORTH);
    
            JTable surveyTable = createSurveyTable();
            JScrollPane scrollPane = new JScrollPane(surveyTable);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
    
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                frame.dispose();
                surveyui.showCrudOptions();
            });
            mainPanel.add(backButton, BorderLayout.SOUTH);
    
            frame.add(mainPanel);
            frame.setVisible(true);
        }
    
        private JTable createSurveyTable() {
            String[] columnNames = {"ID","Update at","Created at", "Description", "Name"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
            List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
            if (!surveys.isEmpty()) {
                for (Survey survey : surveys) {
                    Object[] rowData = {
                        survey.getId(),
                        survey.getUpdated_at(),
                        survey.getCreated_at(),
                        survey.getDescription(),
                        survey.getName()
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No surveys found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
    
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            return table;
        }
    }
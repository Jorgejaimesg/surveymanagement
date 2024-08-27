package com.surveymanagement.question.infrastructure.questionui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.surveymanagement.question.application.FindAllQuestionUseCase;
import com.surveymanagement.question.domain.entity.Question;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;

public class FindAllQuestionUI {
    private final FindAllQuestionUseCase findAllQuestionUseCase;
        private final QuestionUI questionui;
        private JFrame frame;
    
        public FindAllQuestionUI(FindAllQuestionUseCase findAllQuestionUseCase, QuestionUI questionUI) {
            this.findAllQuestionUseCase = findAllQuestionUseCase;
            this.questionui = questionUI;
        }
    
        public void showAllQuestions() {
            frame = new JFrame("All Questions");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
    
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            JLabel titleLabel = new JLabel("All Questions");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(titleLabel, BorderLayout.NORTH);
    
            JTable questionTable = createQuestionTable();
            JScrollPane scrollPane = new JScrollPane(questionTable);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
    
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                frame.dispose();
                questionui.showCrudOptions();
            });
            mainPanel.add(backButton, BorderLayout.SOUTH);
    
            frame.add(mainPanel);
            frame.setVisible(true);
        }
    
        private JTable createQuestionTable() {
            String[] columnNames = {"ID","Created at","Update at", "question_number","response_type","comment_question","question_text"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
            List<Question> questions = findAllQuestionUseCase.findAllQuestion();
            if (!questions.isEmpty()) {
                for (Question question : questions) {
                    Object[] rowData = {
                        question.getId(),
                        question.getCreated_at(),
                        question.getUpdated_at(),
                        question.getQuestion_number(),
                        question.getResponse_type(),
                        question.getComment_question(),
                        question.getQuestion_text()
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No questions found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
    
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            return table;
        }
    }
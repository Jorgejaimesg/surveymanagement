package com.surveymanagement.chapter.infrastructure.chapterui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.surveymanagement.chapter.application.FindAllChapterUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;

public class FindAllChapterUI {
    private final FindAllChapterUseCase findAllChapterUseCase;
        private final ChapterUI chapterui;
        private JFrame frame;
    
        public FindAllChapterUI(FindAllChapterUseCase findAllChapterUseCase, ChapterUI chapterUI) {
            this.findAllChapterUseCase = findAllChapterUseCase;
            this.chapterui = chapterUI;
        }
    
        public void showAllChapters() {
            frame = new JFrame("All Chapters");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
    
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            JLabel titleLabel = new JLabel("All Chapters");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(titleLabel, BorderLayout.NORTH);
    
            JTable chapterTable = createChapterTable();
            JScrollPane scrollPane = new JScrollPane(chapterTable);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
    
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                frame.dispose();
                chapterui.showCrudOptions();
            });
            mainPanel.add(backButton, BorderLayout.SOUTH);
    
            frame.add(mainPanel);
            frame.setVisible(true);
        }
    
        private JTable createChapterTable() {
            String[] columnNames = {"ID","Created at","survey ID","Update at", "Number", "Title"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
            List<Chapter> chapters = findAllChapterUseCase.findAllChapter();
            if (!chapters.isEmpty()) {
                for (Chapter chapter : chapters) {
                    Object[] rowData = {
                        chapter.getId(),
                        chapter.getCreated_at(),
                        chapter.getSurvey_id(),
                        chapter.getUpdated_at(),
                        chapter.getChapter_number(),
                        chapter.getChapter_title()
                    };
                    model.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No chapters found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
    
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            return table;
        }
    }
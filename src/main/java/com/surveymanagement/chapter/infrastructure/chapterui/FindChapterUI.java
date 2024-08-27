package com.surveymanagement.chapter.infrastructure.chapterui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.chapter.application.FindChapterByCodeUseCase;
import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;


public class FindChapterUI extends JFrame{
    private final ChapterUI chapterui;

    public FindChapterUI(
            FindChapterByCodeUseCase findChapterByCodeUseCase, ChapterUI chapterui) {
        this.chapterui = chapterui;
    }

    private JComboBox<String> chapterBox, surveyBox;
    private JTextArea resultArea;
    int surveyID;
    
    public void showFindChapter(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Chapter");
        setSize(500, 500);
        
        ChapterService chapterService = new ChapterRepository();
        FindChapterByCodeUseCase findChapterByCodeyUseCase = new FindChapterByCodeUseCase(chapterService);
        initComponents(findChapterByCodeyUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindChapterByCodeUseCase findChapterByCodeyUseCase){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Chapter");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
    
        JLabel labelSurvey = new JLabel("Survey:");
        addComponent(labelSurvey, 1, 0, 1);
        JLabel labelChapter = new JLabel("Chapter:");
        addComponent(labelChapter, 2, 0, 1);

        surveyBox = new JComboBox<>();
        SurveyService surveyService = new SurveyRepository();
        FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
        List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
        for (Survey survey : surveys) {
            surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
        }
        addComponent(surveyBox, 1, 1, 1);

        chapterBox = new JComboBox<>();
        surveyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateChapterBox();
            }
        });
        addComponent(chapterBox, 2, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findChapter());
        addComponent(btnFind, 3, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            chapterui.showCrudOptions();
        });
        addComponent(btnClose, 5, 0, 2);
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



        private void findChapter() {
        int chapterCode = Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString()));
        ChapterService chapterService = new ChapterRepository();
        FindChapterByCodeUseCase findChapterByCodeUseCase = new FindChapterByCodeUseCase(chapterService);
        Optional<Chapter> foundChapter = findChapterByCodeUseCase.findChapterByCode(chapterCode);
        
        if (foundChapter.isPresent()) {
            String message = String.format(
                "Chapter found successfully:\n\n" +
                "ID: %d\n" +
                "Number: %s\n" +
                "Title: %s\n.",
                foundChapter.get().getId(),
                foundChapter.get().getChapter_number(),
                foundChapter.get().getChapter_title()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Chapter deletion failed. Chapter not found.");
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

    private void updateChapterBox() {
        chapterBox.removeAllItems(); 
        int surveyid = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
        SurveyService surveyService = new SurveyRepository();
        FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
        Optional<Survey> surveyFound = findSurveyByCodeUseCase.findSurveyByCode(surveyid);
        if (surveyFound.isPresent()){
        this.surveyID =surveyFound.get().getId();
        ChapterService chapterService = new ChapterRepository();
        FindChapterBySurveyUseCase findChapterBySurveyUseCase = new FindChapterBySurveyUseCase(chapterService);
        List<Chapter> Chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyID);
        for(Chapter Chapteritem : Chapters){
            chapterBox.addItem(Chapteritem.getId()+". "+ Chapteritem.getChapter_title());
        };
    }}

}

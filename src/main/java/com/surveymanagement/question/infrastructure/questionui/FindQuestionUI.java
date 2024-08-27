package com.surveymanagement.question.infrastructure.questionui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.question.application.FindQuestionByCodeUseCase;
import com.surveymanagement.question.application.FindQuestionByChapterUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;
import com.surveymanagement.question.infrastructure.QuestionRepository;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.application.FindChapterByCodeUseCase;
import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;

public class FindQuestionUI extends JFrame{
    private final QuestionUI questionui;

    public FindQuestionUI(
            FindQuestionByCodeUseCase findQuestionByCodeUseCase, QuestionUI questionui) {
        this.questionui = questionui;
    }

    private JComboBox<String> questionBox, chapterBox, surveyBox;
    private JTextArea resultArea;
    int chapterID, surveyID;
    
    public void showFindQuestion(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Question");
        setSize(500, 500);
        
        QuestionService questionService = new QuestionRepository();
        FindQuestionByCodeUseCase findQuestionByCodeyUseCase = new FindQuestionByCodeUseCase(questionService);
        initComponents(findQuestionByCodeyUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindQuestionByCodeUseCase findQuestionByCodeyUseCase){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Question");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
    
        JLabel labelSurvey = new JLabel("Survey:");
        addComponent(labelSurvey, 1, 0, 1);
        JLabel labelChapter = new JLabel("Chapter:");
        addComponent(labelChapter, 2, 0, 1);
        JLabel labelQuestion = new JLabel("Question:");
        addComponent(labelQuestion, 3, 0, 1);

        surveyBox = new JComboBox<String>();
        SurveyService surveyBoxService = new SurveyRepository();
        FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyBoxService);
        List<Survey> surveyBoxs = findAllSurveyUseCase.findAllSurvey();
        for (Survey surveyitem : surveyBoxs) {
            surveyBox.addItem(String.valueOf(surveyitem.getId())+". " + surveyitem.getName());
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
        questionBox = new JComboBox<>();
        chapterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestionBox();
            }
        });
        addComponent(questionBox, 3, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findQuestion());
        addComponent(btnFind, 4, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            questionui.showCrudOptions();
        });
        addComponent(btnClose, 6, 0, 2);
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



        private void findQuestion() {
        int questionCode = Integer.parseInt(TextBeforeDot(questionBox.getSelectedItem().toString()));
        QuestionService questionService = new QuestionRepository();
        FindQuestionByCodeUseCase findQuestionByCodeUseCase = new FindQuestionByCodeUseCase(questionService);
        Optional<Question> foundQuestion = findQuestionByCodeUseCase.findQuestionByCode(questionCode);
        
        if (foundQuestion.isPresent()) {
            String message = String.format(
                "Question found successfully:\n\n" +
                "ID: %d\n" +
                "Number: %s\n" +
                "Type: %s\n" +
                "Comment: %s\n" +
                "Question: %s\n.",
                foundQuestion.get().getId(),
                foundQuestion.get().getQuestion_number(),
                foundQuestion.get().getResponse_type(),
                foundQuestion.get().getComment_question(),
                foundQuestion.get().getQuestion_text()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Question deletion failed. Question not found.");
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

    private void updateQuestionBox() {
        questionBox.removeAllItems(); 
        int chapterid = Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString()));
        ChapterService chapterService = new ChapterRepository();
        FindChapterByCodeUseCase findChapterByCodeUseCase = new FindChapterByCodeUseCase(chapterService);
        Optional<Chapter> chapterFound = findChapterByCodeUseCase.findChapterByCode(chapterid);
        if (chapterFound.isPresent()){
        this.chapterID =chapterFound.get().getId();
        QuestionService questionService = new QuestionRepository();
        FindQuestionByChapterUseCase findQuestionByChapterUseCase = new FindQuestionByChapterUseCase(questionService);
        List<Question> Questions = findQuestionByChapterUseCase.findQuestionByChapter(chapterID);
        for(Question Questionitem : Questions){
            questionBox.addItem(Questionitem.getId()+". "+ Questionitem.getQuestion_text());
        };
    }}

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

package com.surveymanagement.responseoption.infrastructure.responseoptionui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.responseoption.application.FindResponseOptionByIdUseCase;
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;
import com.surveymanagement.responseoption.infrastructure.ResponseOptionRepository;
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
import com.surveymanagement.question.application.FindQuestionByChapterUseCase;
import com.surveymanagement.question.application.FindQuestionByCodeUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;
import com.surveymanagement.question.infrastructure.QuestionRepository;

public class FindResponseOptionUI extends JFrame{
    private final ResponseOptionUI responseoptionui;

    public FindResponseOptionUI(
            FindResponseOptionByIdUseCase findResponseOptionByCodeUseCase, ResponseOptionUI responseoptionui) {
        this.responseoptionui = responseoptionui;
    }

    private JComboBox<String> responseoptionBox, chapterBox, surveyBox, questionBox;
    private JTextArea resultArea;
    int chapterID, surveyID,questionID;
    
    public void showFindResponseOption(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Response Option");
        setSize(500, 500);
        
        ResponseOptionService responseoptionService = new ResponseOptionRepository();
        FindResponseOptionByIdUseCase findResponseOptionByCodeyUseCase = new FindResponseOptionByIdUseCase(responseoptionService);
        initComponents(findResponseOptionByCodeyUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindResponseOptionByIdUseCase findResponseOptionByCodeyUseCase){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Response Option");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
    
        JLabel labelSurvey = new JLabel("Survey:");
        addComponent(labelSurvey, 1, 0, 1);
        JLabel labelChapter = new JLabel("Chapter:");
        addComponent(labelChapter, 2, 0, 1);
        JLabel labelQuestion = new JLabel("Question:");
        addComponent(labelQuestion, 3, 0, 1);
        JLabel labelResponseOption = new JLabel("ResponseOption:");
        addComponent(labelResponseOption, 4, 0, 1);

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

        questionBox = new JComboBox<>();
        chapterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestionBox();
            }
        });
        addComponent(questionBox, 3, 1, 1);
        
        responseoptionBox = new JComboBox<>();
        questionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateResponseOptionBox();
            }
        });
        addComponent(responseoptionBox, 4, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findResponseOption());
        addComponent(btnFind, 5, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            responseoptionui.showCrudOptions();
        });
        addComponent(btnClose, 7, 0, 2);
        revalidate(); // Asegura que el layout se actualice
        repaint(); // Redibuja la ventana
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



        private void findResponseOption() {
        int responseoptionCode = Integer.parseInt(TextBeforeDot(responseoptionBox.getSelectedItem().toString()));
        ResponseOptionService responseoptionService = new ResponseOptionRepository();
        FindResponseOptionByIdUseCase findResponseOptionByCodeUseCase = new FindResponseOptionByIdUseCase(responseoptionService);
        Optional<ResponseOption> foundResponseOption = findResponseOptionByCodeUseCase.execute(responseoptionCode);
        
        if (foundResponseOption.isPresent()) {
            String message = String.format(
                "Response Option found:\n\n" +
                "Id: %d\n" +
                "Value: %s\n" +
                "Category Catalog ID: %s\n" +
                "Parent Response: %s\n" +
                "Question ID: %s\n" +
                "Comment: %s\n" +
                "Text: %s\n",
                foundResponseOption.get().getId(),
                foundResponseOption.get().getOptionValue(),
                foundResponseOption.get().getCategoryCatalogId(),
                foundResponseOption.get().getParentResponseId(),
                foundResponseOption.get().getQuestionId(),
                foundResponseOption.get().getCommentResponse(),
                foundResponseOption.get().getOptionText()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("ResponseOption deletion failed. ResponseOption not found.");
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

    private void updateResponseOptionBox() {
        responseoptionBox.removeAllItems(); 
        int questionid = Integer.parseInt(TextBeforeDot(questionBox.getSelectedItem().toString()));
        QuestionService questionService = new QuestionRepository();
        FindQuestionByCodeUseCase findQuestionByCodeUseCase = new FindQuestionByCodeUseCase(questionService);
        Optional<Question> questionFound = findQuestionByCodeUseCase.findQuestionByCode(questionid);
        if (questionFound.isPresent()){
            this.questionID =questionFound.get().getId();
            ResponseOptionService responseoptionService = new ResponseOptionRepository();
            FindResponseOptionByQuestionUseCase findResponseOptionByQuestionUseCase = new FindResponseOptionByQuestionUseCase(responseoptionService);
            List<ResponseOption> ResponseOptions = findResponseOptionByQuestionUseCase.execute(questionID);
            for(ResponseOption ResponseOptionitem : ResponseOptions){
                responseoptionBox.addItem(ResponseOptionitem.getId()+". "+ ResponseOptionitem.getOptionText());
            };
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


}

package com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui;

import java.util.List;
import java.util.Optional;

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
import com.surveymanagement.question.application.FindQuestionByChapterUseCase;
import com.surveymanagement.question.application.FindQuestionByCodeUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;
import com.surveymanagement.question.infrastructure.QuestionRepository;
import com.surveymanagement.responseoption.application.FindResponseOptionByIdUseCase;
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;
import com.surveymanagement.responseoption.infrastructure.ResponseOptionRepository;
import com.surveymanagement.subresponseoption.application.FindSubResponseOptionByIdUseCase;
import com.surveymanagement.subresponseoption.application.DeleteSubResponseOptionUseCase;
import com.surveymanagement.subresponseoption.application.FindSubResponseOptionByResponseOptionUseCase;
import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;
import com.surveymanagement.subresponseoption.infrastructure.SubResponseOptionRepository;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class DeleteSubResponseOptionUi extends JFrame{
    private final DeleteSubResponseOptionUseCase deleteSubResponseOptionUseCase;
    private final SubResponseOptionUi responseOptionUi;

 



    public DeleteSubResponseOptionUi(DeleteSubResponseOptionUseCase deleteSubResponseOptionUseCase, SubResponseOptionUi responseOptionUi) {
        this.deleteSubResponseOptionUseCase = deleteSubResponseOptionUseCase;
        this.responseOptionUi = responseOptionUi;
    }

    private JComboBox<String> responseoptionBox, chapterBox, surveyBox, questionBox ,subresponseoptionBox;
    private JTextArea resultArea;
    int chapterID, surveyID, questionID, responseOptionID;  //////////////revisar aca
    
    public void showDeleteSubResponseOption(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Subresponse Option");
        setSize(500, 600);
        
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete SubresponseOption");
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
        JLabel labelSubResponseOption = new JLabel("SubResponseOption:");
        addComponent(labelSubResponseOption, 5, 0, 1);

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
       
        subresponseoptionBox = new JComboBox<>();
        responseoptionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSubResponseOptionBox();
            }
        });
        addComponent(subresponseoptionBox, 5, 1, 1);

        JButton btnFind = new JButton("Delete");
        btnFind.addActionListener(e -> findSubResponseOption());
        addComponent(btnFind, 6, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            responseOptionUi.showCrudOptions();
        });
        addComponent(btnClose, 8, 0, 2);
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



        private void findSubResponseOption() {
        int subresponseoptionCode = Integer.parseInt(TextBeforeDot(subresponseoptionBox.getSelectedItem().toString()));
        
        SubResponseOptionService subResponseoptionService = new SubResponseOptionRepository();
        FindSubResponseOptionByIdUseCase findSubResponseOptionByIdUseCase = new FindSubResponseOptionByIdUseCase(subResponseoptionService);
        Optional<SubResponseOption> foundSubResponseOption = findSubResponseOptionByIdUseCase.execute(subresponseoptionCode);
        
        if (foundSubResponseOption.isPresent()) {
            deleteSubResponseOptionUseCase.execute(foundSubResponseOption.get().getId());
            String message = String.format(
                "SubResponseOption deleted successfully:\n\n" +
                "Id: %d\n" +
                "SubResponseNumber: %d\n" +
                "CreatedAt: %s\n" +
                "ResponseOptionId: %d\n" +
                "UpdateAt: %s\n" +
                "SubResponseText: %s\n",
                foundSubResponseOption.get().getId(),
                foundSubResponseOption.get().getSubResponseNumber(),
                foundSubResponseOption.get().getCreatedAt(),
                foundSubResponseOption.get().getResponseOptionId(),
                foundSubResponseOption.get().getUpdateAt(),
                foundSubResponseOption.get().getSubResponseText()
   
            );
            resultArea.setText(message);
            revalidate(); // Asegura que el layout se actualice
            repaint(); // Redibuja la ventana
        } else {
            resultArea.setText("SubResponseOption deletion failed. ResponseOption not found.");
        }
    }

    private String TextBeforeDot(String text) {
        int position = text.indexOf('.');
        if (position != -1) {
            return text.substring(0, position);
        } else {
            return text;
        }
    }

    private void updateChapterBox() {
        try{

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
            }
        }catch(Exception ex){

        }
    }
    
    private void updateQuestionBox() { 
        try{
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
            }

        }catch(Exception ex){

        }
    }
        
        private void updateResponseOptionBox() {
            try{

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
            }catch(Exception ex){

            }
        }
    private void updateSubResponseOptionBox() {
        try{

            subresponseoptionBox.removeAllItems(); 
            int responseoptionid = Integer.parseInt(TextBeforeDot(responseoptionBox.getSelectedItem().toString()));
    
            ResponseOptionService responseOptionService = new ResponseOptionRepository();
            FindResponseOptionByIdUseCase findResponseOptionByIdUseCase = new FindResponseOptionByIdUseCase(responseOptionService);
           
            Optional<ResponseOption> reponseoptionFound = findResponseOptionByIdUseCase.execute(responseoptionid);
            if (reponseoptionFound.isPresent()){
            this.responseOptionID =reponseoptionFound.get().getId();
            
            SubResponseOptionService subResponseOptionService = new SubResponseOptionRepository();
            FindSubResponseOptionByResponseOptionUseCase findSubResponseOptionByResponseOptionUseCase = new FindSubResponseOptionByResponseOptionUseCase(subResponseOptionService);
            
            List<SubResponseOption> subResponseOptions = findSubResponseOptionByResponseOptionUseCase.execute(responseOptionID);
            for(SubResponseOption subResponseOptionitem : subResponseOptions){
                subresponseoptionBox.addItem(subResponseOptionitem.getId()+". "+ subResponseOptionitem.getSubResponseText());
            };
            
            }
        }catch(Exception ex){

        }
    }


}

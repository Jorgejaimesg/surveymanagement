package com.surveymanagement.responseoption.infrastructure.responseoptionui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;
import com.surveymanagement.categorycatalog.infrastructure.CategoryCatalogRepository;
import com.surveymanagement.chapter.application.FindChapterByCodeUseCase;
import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;
import com.surveymanagement.question.application.FindQuestionByChapterUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;
import com.surveymanagement.question.infrastructure.QuestionRepository;
import com.surveymanagement.responseoption.application.CreateResponseOptionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;


public class AddResponseOptionUI  extends JFrame {
    private final CreateResponseOptionUseCase createResponseOptionUseCase;
    private final ResponseOptionUI responseOptionUI;

    public AddResponseOptionUI(
            CreateResponseOptionUseCase createResponseOptionUseCase,
            ResponseOptionUI responseOptionUI) {
        this.createResponseOptionUseCase = createResponseOptionUseCase;
        this.responseOptionUI = responseOptionUI;

    }
    private JTextField option_value, parentresponse_id, comment_response, option_text;
    private JButton create, goback, reset ;
    private JComboBox<String> surveyBox, chapterBox, QuestionBox, categorycatalogBox;
    private JCheckBox isCatalog, isParent; 
    
    public void frmRegResponseOption(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Response Option");
        setSize(500, 500);

        JLabel title = new JLabel("Create Response Option");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);


        option_value = new JTextField();
        parentresponse_id = new JTextField();
        parentresponse_id.setEnabled(false);
        comment_response = new JTextField();
        option_text = new JTextField();
        
        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        
        isCatalog = new JCheckBox();
        isCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isCatalog.isSelected()){
                    categorycatalogBox.setVisible(true);
                } else {
                    categorycatalogBox.setVisible(false);
                }
            }});
        isParent = new JCheckBox();
        isParent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isParent.isSelected()){
                    parentresponse_id.setEnabled(true);
                } else {
                    parentresponse_id.setEnabled(false);
                    parentresponse_id.setText("");
                }
            }});

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoyResponseOption());
        goback.addActionListener(e -> {
            dispose();
            responseOptionUI.showCrudOptions(); 
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        surveyBox = new JComboBox<>();
        SurveyService surveyService = new SurveyRepository();
        FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
        List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
        for (Survey survey : surveys) {
            surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
        }
        
        chapterBox = new JComboBox<>();
        surveyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateChapterBox();
            }
        });

        QuestionBox = new JComboBox<>();
        chapterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestionBox();
            }
        });

        categorycatalogBox = new JComboBox<>();
        categorycatalogBox.setVisible(false);
        CategoryCatalogService categoryCatalogService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(categoryCatalogService);
        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog categoryCatalog : categorycatalogs){
            categorycatalogBox.addItem(String.valueOf(categoryCatalog.getId())+". "+ categoryCatalog.getName());
        }

        addComponent(title, 0, 1, 2);
        addComponent(new JLabel("Survey:"), 1, 1, 1);
        addComponent(surveyBox, 1, 2, 1);
        addComponent(new JLabel("Chapter:"), 2, 1, 1);
        addComponent(chapterBox, 2, 2, 1);
        addComponent(new JLabel("Question:"), 3, 1, 1);
        addComponent(QuestionBox, 3, 2, 1);
        addComponent(new JLabel("Value:"), 4, 1, 1);
        addComponent(option_value, 4, 2, 1);
        addComponent(new JLabel("Category Catalog:"), 5, 1, 1);
        addComponent(isCatalog, 5, 0, 1);
        addComponent(categorycatalogBox, 5, 2, 1);
        addComponent(new JLabel("Parent:"), 6, 1, 1);
        addComponent(isParent, 6, 0, 1);
        addComponent(parentresponse_id, 6, 2, 1);
        addComponent(new JLabel("comment response:"), 7, 1, 1);
        addComponent(comment_response, 7, 2, 1);
        addComponent(new JLabel("Text:"), 8, 1, 1);
        addComponent(option_text, 8, 2, 1);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(reset);
        buttonPanel.add(goback);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
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

    private void saveCategoyResponseOption() {
        try {
            ResponseOption responseOption = new ResponseOption();
            if(isCatalog.isSelected()){

                responseOption.setCategoryCatalogId(Integer.parseInt(TextBeforeDot(categorycatalogBox.getSelectedItem().toString())));
            } else {
                responseOption.setCategoryCatalogId(0);
            }
            responseOption.setOptionValue(Integer.parseInt(option_value.getText().trim()));
            responseOption.setQuestionId(Integer.parseInt(TextBeforeDot(QuestionBox.getSelectedItem().toString())));
            
            if(isParent.isSelected()){

                responseOption.setParentResponseId(Integer.parseInt(parentresponse_id.getText().trim()));
            } else{
                responseOption.setParentResponseId(0);
            }
            responseOption.setCommentResponse(comment_response.getText().trim());
            responseOption.setOptionText(option_text.getText().toString().trim());
            

            createResponseOptionUseCase.execute(responseOption); // Corrected from "customer" to "role"
            JOptionPane.showMessageDialog(this, "ResponseOption added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        option_value.setText("");
        parentresponse_id.setText("");
        comment_response.setText("");
        option_text.setText("");
        
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
            SurveyService surveyService = new SurveyRepository();
            ChapterService chapterService = new ChapterRepository();
            FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
            FindChapterBySurveyUseCase findChapterBySurveyUseCase = new FindChapterBySurveyUseCase(chapterService);
            
            
            chapterBox.removeAllItems(); 
            int surveyID = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
            Optional<Survey> surveyFound = findSurveyByCodeUseCase.findSurveyByCode(surveyID);
            if (surveyFound.isPresent()){
                List<Chapter> Chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyID);
                for(Chapter Chapteritem : Chapters){
                    chapterBox.addItem(Chapteritem.getId()+". "+ Chapteritem.getChapter_title());
                };
        }}

    private void updateQuestionBox() {
        QuestionBox.removeAllItems(); 
        int chapterid = Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString()));
        ChapterService chapterService = new ChapterRepository();
        FindChapterByCodeUseCase findChapterByCodeUseCase = new FindChapterByCodeUseCase(chapterService);
        Optional<Chapter> chapterFound = findChapterByCodeUseCase.findChapterByCode(chapterid);
        if (chapterFound.isPresent()){
        int chapterID =chapterFound.get().getId();
        QuestionService questionService = new QuestionRepository();
        FindQuestionByChapterUseCase findQuestionByChapterUseCase = new FindQuestionByChapterUseCase(questionService);
        List<Question> Questions = findQuestionByChapterUseCase.findQuestionByChapter(chapterID);
        for(Question Questionitem : Questions){
            QuestionBox.addItem(Questionitem.getId()+". "+ Questionitem.getQuestion_text());
        };
    }}
}

package com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui;

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
import com.surveymanagement.subresponseoption.application.CreateSubResponseOptionUseCase;
import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;
import com.surveymanagement.responseoption.infrastructure.ResponseOptionRepository;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class CreateSubResponseOptionUi extends JFrame{
    private final CreateSubResponseOptionUseCase createSubResponseOptionUseCase;
    private final SubResponseOptionUi subResponseOptionUi;


    public CreateSubResponseOptionUi(CreateSubResponseOptionUseCase createSubResponseOptionUseCase,
            SubResponseOptionUi subResponseOptionUi) {
        this.createSubResponseOptionUseCase = createSubResponseOptionUseCase;
        this.subResponseOptionUi = subResponseOptionUi;
    }

    private JTextField subResponseNumberTxt,subResponseText;
    private JButton create, goback, reset ;
    private JComboBox<String> surveyBox, chapterBox, QuestionBox, responseOptionBox;
 
    
    public void frmRegSubResponseOption(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Subresponse Option");
        setSize(600, 500);

        JLabel title = new JLabel("Create Subresponse Option");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

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

        responseOptionBox = new JComboBox<>();
        QuestionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateResponseOptionBox();
            }
        });

        subResponseNumberTxt = new JTextField();
        subResponseText = new JTextField();
  
        
        
        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        
  

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoyResponseOption());
        goback.addActionListener(e -> {
            dispose();
            subResponseOptionUi.showCrudOptions(); 
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;


        addComponent(title, 0, 1, 2);
        addComponent(new JLabel("1. Survey:"), 1, 1, 1);
        addComponent(surveyBox, 1, 2, 1);
        addComponent(new JLabel("2. Chapter:"), 2, 1, 1);
        addComponent(chapterBox, 2, 2, 1);
        addComponent(new JLabel("3. Question:"), 3, 1, 1);
        addComponent(QuestionBox, 3, 2, 1);
        addComponent(new JLabel("4. Response Option:"), 4, 1, 1);
        addComponent(responseOptionBox, 4, 2, 1);
        addComponent(new JLabel("5. Number:"), 5, 1, 1);
        addComponent(subResponseNumberTxt, 5, 2, 1);
        addComponent(new JLabel("6. Text:"), 6, 1, 1);
        addComponent(subResponseText, 6, 2, 1);

        

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
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }

    private void saveCategoyResponseOption() {
        try {
            SubResponseOption subResponseOption = new SubResponseOption();
            
        
            subResponseOption.setResponseOptionId(Integer.parseInt(TextBeforeDot(responseOptionBox.getSelectedItem().toString())));
            subResponseOption.setSubResponseNumber(Integer.parseInt(subResponseNumberTxt.getText().trim()));
            subResponseOption.setSubResponseText(subResponseText.getText().toString().trim());
            

            createSubResponseOptionUseCase.execute(subResponseOption); 
            JOptionPane.showMessageDialog(this, "SubResponseOption added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        subResponseNumberTxt.setText("");
        subResponseText.setText("");
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
        chapterBox.removeAllItems(); 
        SurveyService surveyService = new SurveyRepository();
        ChapterService chapterService = new ChapterRepository();
        FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
        FindChapterBySurveyUseCase findChapterBySurveyUseCase = new FindChapterBySurveyUseCase(chapterService);
        
        
        int surveyID = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
        Optional<Survey> surveyFound = findSurveyByCodeUseCase.findSurveyByCode(surveyID);
        if (surveyFound.isPresent()){
            List<Chapter> Chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyID);
            for(Chapter Chapteritem : Chapters){
                chapterBox.addItem(Chapteritem.getId()+". "+ Chapteritem.getChapter_title());
            };
        }
        revalidate(); // Asegura que el layout se actualice
        repaint();
    }

    private void updateQuestionBox() {
        QuestionBox.removeAllItems(); 
        int chapterid = Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString()));
        
        ChapterService chapterService = new ChapterRepository();
        FindChapterByCodeUseCase findChapterByCodeUseCase = new FindChapterByCodeUseCase(chapterService);
        QuestionService questionService = new QuestionRepository();
        FindQuestionByChapterUseCase findQuestionByChapterUseCase = new FindQuestionByChapterUseCase(questionService);
        

        Optional<Chapter> chapterFound = findChapterByCodeUseCase.findChapterByCode(chapterid);
        if (chapterFound.isPresent()){
        int chapterID =chapterFound.get().getId();

        List<Question> Questions = findQuestionByChapterUseCase.findQuestionByChapter(chapterID);
        for(Question Questionitem : Questions){
            QuestionBox.addItem(Questionitem.getId()+". "+ Questionitem.getQuestion_text());
        };
        revalidate(); // Asegura que el layout se actualice
        repaint();
    }}

    private void updateResponseOptionBox() {
        try{

            responseOptionBox.removeAllItems(); 
            int questionid = Integer.parseInt(TextBeforeDot(QuestionBox.getSelectedItem().toString()));
            
            QuestionService questionService = new QuestionRepository();
            FindQuestionByCodeUseCase findQuestionByCodeUseCase = new FindQuestionByCodeUseCase(questionService);
            ResponseOptionService responseOptionService = new ResponseOptionRepository();
            FindResponseOptionByQuestionUseCase findResponseOptionByQuestionUseCase = new FindResponseOptionByQuestionUseCase(responseOptionService);
            
    
            Optional<Question> questionFound = findQuestionByCodeUseCase.findQuestionByCode(questionid);
            if (questionFound.isPresent()){
            int questionID =questionFound.get().getId();
    
            List<ResponseOption> responseOptions = findResponseOptionByQuestionUseCase.execute(questionID);
            for(ResponseOption ResponseOptionitem : responseOptions){
                responseOptionBox.addItem(ResponseOptionitem.getId()+". "+ ResponseOptionitem.getOptionText());
            };
            revalidate(); // Asegura que el layout se actualice
            repaint();
        }
        }catch(Exception ex){

        }
    }
}

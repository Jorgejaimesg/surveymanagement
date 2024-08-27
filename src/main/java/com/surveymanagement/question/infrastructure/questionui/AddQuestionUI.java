package com.surveymanagement.question.infrastructure.questionui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;
import com.surveymanagement.question.application.CreateQuestionUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;


public class AddQuestionUI  extends JFrame {
    private final CreateQuestionUseCase createQuestionUseCase;
    private final QuestionUI questionUI;

    public AddQuestionUI(
            CreateQuestionUseCase createQuestionUseCase,
            QuestionUI questionUI) {
        this.createQuestionUseCase = createQuestionUseCase;
        this.questionUI = questionUI;

    }
    private JTextField question_number, comment_question, question_text;
    private JButton create, goback, reset ;
    private JComboBox<String> surveyBox, chapterBox, response_type;
    private int surveyID;
    
    public void frmRegQuestion(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Question");
        setSize(500, 500);

        JLabel title = new JLabel("Create Question");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);


        question_number = new JTextField();
        comment_question = new JTextField();
        question_text = new JTextField();
        
        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoyQuestion());
        goback.addActionListener(e -> {
            dispose();
            questionUI.showCrudOptions(); 
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

        
        response_type = new JComboBox<>();
        response_type.addItem("single_choice");
        response_type.addItem("multiple_choice");
        response_type.addItem("open_text");
        
        addComponent(title, 0, 0, 2);
        addComponent(new JLabel("Survey:"), 1, 0, 1);
        addComponent(surveyBox, 1, 1, 1);
        addComponent(new JLabel("Chapter:"), 2, 0, 1);
        addComponent(chapterBox, 2, 1, 1);
        addComponent(new JLabel("Number:"), 3, 0, 1);
        addComponent(question_number, 3, 1, 1);
        addComponent(new JLabel("Response Type:"), 4, 0, 1);
        addComponent(response_type, 4, 1, 1);
        addComponent(new JLabel("Comment Question:"), 5, 0, 1);
        addComponent(comment_question, 5, 1, 1);
        addComponent(new JLabel("Question text:"), 6, 0, 1);
        addComponent(question_text, 6, 1, 1);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(reset);
        buttonPanel.add(goback);
        gbc.gridx = 0;
        gbc.gridy = 7;
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

    private void saveCategoyQuestion() {
        try {
            Question question = new Question();
            question.setChapter_id(Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString())));
            question.setQuestion_number(question_number.getText().trim());
            question.setResponse_type(response_type.getSelectedItem().toString());
            question.setComment_question(comment_question.getText().trim());
            question.setQuestion_text(question_text.getText().trim());
            

            createQuestionUseCase.execute(question); // Corrected from "customer" to "role"
            JOptionPane.showMessageDialog(this, "Question added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        question_number.setText("");
        comment_question.setText("");
        chapterBox.removeAllItems();
        question_text.setText("");
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
            this.surveyID = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
            Optional<Survey> surveyFound = findSurveyByCodeUseCase.findSurveyByCode(surveyID);
            if (surveyFound.isPresent()){
                List<Chapter> Chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyID);
                for(Chapter Chapteritem : Chapters){
                    chapterBox.addItem(Chapteritem.getId()+". "+ Chapteritem.getChapter_title());
                };
        }
    }
}

package com.surveymanagement.chapter.infrastructure.chapterui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.surveymanagement.chapter.application.CreateChapterUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class AddChapterUI extends JFrame {
    private final CreateChapterUseCase createChapterUseCase;
    private final ChapterUI chapterUI;

    public AddChapterUI(
            CreateChapterUseCase createChapterUseCase,
            ChapterUI chapterUI) {
        this.createChapterUseCase = createChapterUseCase;
        this.chapterUI = chapterUI;
    }
    private JTextField chapter_number, chapter_title;
    private JButton create, goback, reset ;
    private JComboBox<String> surveyBox;
    
    public void frmRegChapter(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Chapter");
        setSize(500, 500);

        JLabel title = new JLabel("Create Chapter");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        chapter_number = new JTextField();
        chapter_title = new JTextField();

        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoyChapter());
        goback.addActionListener(e -> {
            dispose();
            chapterUI.showCrudOptions(); 
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
        addComponent(surveyBox, 1, 1, 1);

        addComponent(title, 0, 0, 2);
        addComponent(new JLabel("Survey:"), 1, 0, 1);
        addComponent(new JLabel("Chapter number:"), 2, 0, 1);
        addComponent(chapter_number, 2, 1, 1);
        addComponent(new JLabel("Chapter title:"), 3, 0, 1);
        addComponent(chapter_title, 3, 1, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(reset);
        buttonPanel.add(goback);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

    private void saveCategoyChapter() {
        try {
            Chapter chapter = new Chapter();
            if (chapter_number.getText().trim().length()>0 && chapter_title.getText().trim().length()>0){
            chapter.setChapter_number(chapter_number.getText().trim());
            chapter.setChapter_title(chapter_title.getText().trim());
            chapter.setSurvey_id(Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString())));
            

            createChapterUseCase.execute(chapter); // Corrected from "customer" to "role"
            JOptionPane.showMessageDialog(this, "Chapter added successfully!");
            resetFields();}
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        chapter_number.setText("");
        chapter_title.setText("");
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
}

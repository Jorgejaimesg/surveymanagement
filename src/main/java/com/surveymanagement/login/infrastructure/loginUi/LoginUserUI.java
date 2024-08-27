package com.surveymanagement.login.infrastructure.loginUi;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.surveymanagement.Main;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.domain.entity.Survey;

public class LoginUserUI extends JFrame {
    private final FindAllSurveyUseCase findAllSurveyUseCase;
    private JComboBox<String> surveyBox;
    private JButton Start, Goback;


    public LoginUserUI(FindAllSurveyUseCase findAllSurveyUseCase) {
        this.findAllSurveyUseCase = findAllSurveyUseCase;
    }


    public void FindSurvey(){

        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Role");
        setSize(500, 500);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/survey.png")).getImage());

        JLabel title = new JLabel("Select a survey");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon imagenOriginal = new ImageIcon(getClass().getClassLoader().getResource("images/survey.png"));
        Image imagenRedimensionada = imagenOriginal.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        ImageIcon imagen = new ImageIcon(imagenRedimensionada);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        surveyBox = new JComboBox<>();
        List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
        for (Survey survey : surveys) {
            surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
        }

        Start = new JButton("Start");
        Goback = new JButton("🔙");

        Start.addActionListener(e -> findSurvey());
        Goback.addActionListener(e -> Main.startLoginProcess());
        Goback.addActionListener(e -> dispose());

        JLabel imageLabel = new JLabel(imagen);
        addComponent(imageLabel, 0, 0, 2); 
        addComponent(title, 1, 0, 2);
        addComponent(new JLabel("Survey:"), 2, 0, 1);
        addComponent(surveyBox, 2, 1, 1);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Start);
        buttonPanel.add(Goback);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

    private String TextBeforeDot(String text) {
        // Buscar la posición del primer punto en la cadena
        int position = text.indexOf('.');
        if (position != -1) {
            return text.substring(0, position);
        } else {
            return text;
        }
    }
    private void findSurvey() {
        int surveyId =Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));

        LoginStartSurveyUI loginStartSurveyUI = new LoginStartSurveyUI();
        dispose(); 
        loginStartSurveyUI.startSurvey(surveyId);

    }
}
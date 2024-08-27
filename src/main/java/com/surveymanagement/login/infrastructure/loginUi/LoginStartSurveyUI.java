package com.surveymanagement.login.infrastructure.loginUi;
import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;
import com.surveymanagement.question.application.FindQuestionByChapterUseCase;
import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;
import com.surveymanagement.question.infrastructure.QuestionRepository;
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;
import com.surveymanagement.responseoption.infrastructure.ResponseOptionRepository;
import com.surveymanagement.responsequestion.application.CreateResponseQuestionUseCase;
import com.surveymanagement.responsequestion.domain.entity.ResponseQuestion;
import com.surveymanagement.responsequestion.domain.service.ResponseQuestionService;
import com.surveymanagement.responsequestion.infrastructure.ResponseQuestionRepository;
import com.surveymanagement.subresponseoption.application.FindSubResponseOptionByResponseOptionUseCase;
import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;
import com.surveymanagement.subresponseoption.infrastructure.SubResponseOptionRepository;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;
import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginStartSurveyUI extends JFrame {
    private JButton Next, Goback;
    private Map<Integer, JLabel> labelMap = new HashMap<>();
    private Map<Integer, JCheckBox> responseOptionCheckboxMap = new HashMap<>();
    private Map<Integer, List<JCheckBox>> subResponseOptionCheckboxMap = new HashMap<>();
    private JScrollPane scrollPane;

    private Map<Integer, String> selectedResponseOptions = new HashMap<>(); // Map to store selected ResponseOptions
    private Map<Integer, String> selectedSubResponseOptions = new HashMap<>(); // Map to store selected SubResponseOptions

    public LoginStartSurveyUI() {
    }

    public void startSurvey(int surveyId){
        initComponents(surveyId);
        setVisible(true);
    }

    private void initComponents(int surveyId){
        SurveyService surveyService = new SurveyRepository();
        FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
        Optional<Survey> foundSurvey = findSurveyByCodeUseCase.findSurveyByCode(surveyId);

        ChapterService chapterService = new ChapterRepository();
        FindChapterBySurveyUseCase findChapterBySurveyUseCase = new FindChapterBySurveyUseCase(chapterService);
        List<Chapter> chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyId);

        QuestionService questionService = new QuestionRepository();
        FindQuestionByChapterUseCase findQuestionByChapterUseCase = new FindQuestionByChapterUseCase(questionService);

        ResponseOptionService responseOptionService = new ResponseOptionRepository();
        FindResponseOptionByQuestionUseCase responseOptionByQuestionUseCase = new FindResponseOptionByQuestionUseCase(responseOptionService);

        SubResponseOptionService subResponseOptionService = new SubResponseOptionRepository();
        FindSubResponseOptionByResponseOptionUseCase findSubResponseOptionByResponseOptionUseCase = new FindSubResponseOptionByResponseOptionUseCase(subResponseOptionService);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Start Survey");
        setSize(700, 600); 
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/survey.png")).getImage());

        JLabel title = new JLabel(foundSurvey.get().getName());
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        for (Chapter chapter : chapters) {
            JLabel label = new JLabel(chapter.getChapter_title());
            labelMap.put(chapter.getId(), label);
            gbc.gridy = row++;
            gbc.gridx = 0;
            contentPanel.add(label, gbc);
            List<Question> questions = findQuestionByChapterUseCase.findQuestionByChapter(chapter.getId());
            for (Question question : questions) {
                JLabel labelquestion = new JLabel(question.getQuestion_text());
                labelMap.put(question.getId(), labelquestion);
                gbc.gridy = row++;
                gbc.gridx = 1;
                contentPanel.add(labelquestion, gbc);
                
                if ("single_choice".equals(question.getResponse_type())){
                    ButtonGroup buttonGroup = new ButtonGroup();
                    List<ResponseOption> responseOptions = responseOptionByQuestionUseCase.execute(question.getId());
                
                    for (ResponseOption responseOption : responseOptions) {
                        if (responseOption.getParentResponseId() == 0) {  
                            JRadioButton radioButton = new JRadioButton(responseOption.getOptionText());
                            gbc.gridy = row++;
                            gbc.gridx = 1;
                            contentPanel.add(radioButton, gbc);
                            buttonGroup.add(radioButton);

                            // Save the selected response in the map
                            radioButton.addActionListener(e -> {
                                selectedResponseOptions.put(responseOption.getId(), responseOption.getOptionText());
                            });
                
                            for (ResponseOption subOption : responseOptions) {
                                if (subOption.getParentResponseId() == responseOption.getId()) {  
                                    JRadioButton subRadioButton = new JRadioButton(subOption.getOptionText());
                                    subRadioButton.setVisible(false);
                                    gbc.gridy = row++;
                                    gbc.gridx = 2;
                                    contentPanel.add(subRadioButton, gbc);

                                    ButtonGroup subButtonGroup = new ButtonGroup();
                
                                    List<SubResponseOption> subResponseOptions = findSubResponseOptionByResponseOptionUseCase.execute(subOption.getId());
                                    List<JRadioButton> subOptionButtons = new ArrayList<>();
                                    
                                    for (SubResponseOption subResponseOption : subResponseOptions) {
                                        JRadioButton subSubOptionButton = new JRadioButton(subResponseOption.getSubResponseText());
                                        subSubOptionButton.setVisible(false);
                                        gbc.gridy = row++;
                                        gbc.gridx = 3;
                                        contentPanel.add(subSubOptionButton, gbc);
                                        subButtonGroup.add(subSubOptionButton);
                                        subOptionButtons.add(subSubOptionButton);

                                        // Save the selected sub-response in the map
                                        subSubOptionButton.addActionListener(e -> {
                                            selectedSubResponseOptions.put(subResponseOption.getId(), subResponseOption.getSubResponseText());
                                        });
                                    }
                
                                    subRadioButton.addItemListener(e -> {
                                        boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
                                        for (JRadioButton subOptionButton : subOptionButtons) {
                                            subOptionButton.setVisible(selected);
                                        }
                                    });
                
                                    radioButton.addItemListener(e -> {
                                        boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
                                        subRadioButton.setVisible(selected);
                                    });
                                }
                            }
                
                            List<SubResponseOption> subResponseOptions = findSubResponseOptionByResponseOptionUseCase.execute(responseOption.getId());
                            ButtonGroup subButtonGroup = new ButtonGroup();
                            List<JRadioButton> subOptionButtons = new ArrayList<>();
                
                            for (SubResponseOption subResponseOption : subResponseOptions) {
                                JRadioButton subSubOptionButton = new JRadioButton(subResponseOption.getSubResponseText());
                                subSubOptionButton.setVisible(false);
                                gbc.gridy = row++;
                                gbc.gridx = 2;
                                contentPanel.add(subSubOptionButton, gbc);
                                subButtonGroup.add(subSubOptionButton);
                                subOptionButtons.add(subSubOptionButton);

                                // Save the selected sub-response in the map
                                subSubOptionButton.addActionListener(e -> {
                                    selectedSubResponseOptions.put(subResponseOption.getId(), subResponseOption.getSubResponseText());
                                });
                            }
                
                            radioButton.addItemListener(e -> {
                                boolean selected = (e.getStateChange() == ItemEvent.SELECTED);
                                for (JRadioButton subOptionButton : subOptionButtons) {
                                    subOptionButton.setVisible(selected);
                                }
                            });
                        }
                    }
                } else if("multiple_choice".equals(question.getResponse_type())){
                    List<ResponseOption> responseOptions = responseOptionByQuestionUseCase.execute(question.getId());
                    for (ResponseOption responseOption : responseOptions) {
                        JCheckBox checkBox = new JCheckBox(responseOption.getOptionText());
                        gbc.gridy = row++;
                        gbc.gridx = 1;
                        contentPanel.add(checkBox, gbc);

                        // Save the selected response in the map
                        checkBox.addActionListener(e -> {
                            if (checkBox.isSelected()) {
                                selectedResponseOptions.put(responseOption.getId(), responseOption.getOptionText());
                            } else {
                                selectedResponseOptions.remove(responseOption.getId());
                            }
                        });

                        List<SubResponseOption> subResponseOptions = findSubResponseOptionByResponseOptionUseCase.execute(responseOption.getId());
                        for (SubResponseOption subresponseOption : subResponseOptions) {
                            JCheckBox checkBoxSubRes = new JCheckBox(subresponseOption.getSubResponseText());
                            gbc.gridy = row++;
                            gbc.gridx = 2;
                            contentPanel.add(checkBoxSubRes, gbc);

                            // Save the selected sub-response in the map
                            checkBoxSubRes.addActionListener(e -> {
                                if (checkBoxSubRes.isSelected()) {
                                    selectedSubResponseOptions.put(subresponseOption.getId(), subresponseOption.getSubResponseText());
                                } else {
                                    selectedSubResponseOptions.remove(subresponseOption.getId());
                                }
                            });
                        }
                    }
                } else {
                    JTextField textField = new JTextField();
                    gbc.gridy = row++;
                    gbc.gridx = 1;
                    contentPanel.add(textField, gbc);

                    // Save the text input in the map (if required)
                    textField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            selectedResponseOptions.put(question.getId(), textField.getText());
                        }
                    });
                }
            }
        }

        scrollPane = new JScrollPane(contentPanel);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        Next = new JButton("Next");
        Goback = new JButton("🔙");
        Goback.addActionListener(e -> goback());
        Next.addActionListener(e -> submitResponses()); // ActionListener for capturing responses
        buttonPanel.add(Next);
        buttonPanel.add(Goback);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void submitResponses() {
        ResponseQuestionService responseQuestionService = new ResponseQuestionRepository();
        CreateResponseQuestionUseCase createResponseQuestionUseCase = new CreateResponseQuestionUseCase(responseQuestionService);

        // Handle selected ResponseOptions
        System.out.println("Selected ResponseOptions:");
        for (Map.Entry<Integer, String> entry : selectedResponseOptions.entrySet()) {
            System.out.println("ResponseOption ID: " + entry.getKey() + ", Text: " + entry.getValue());
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setResponseId(entry.getKey() );
            responseQuestion.setResponseText(entry.getValue());
            responseQuestion.setSubresponseId(0);
            createResponseQuestionUseCase.execute(responseQuestion);
        }

        // Handle selected SubResponseOptions
        System.out.println("Selected SubResponseOptions:");
        for (Map.Entry<Integer, String> entry : selectedSubResponseOptions.entrySet()) {
            System.out.println("SubResponseOption ID: " + entry.getKey() + ", Text: " + entry.getValue());
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setResponseId(0);
            responseQuestion.setResponseText(entry.getValue());
            responseQuestion.setSubresponseId(entry.getKey());
            createResponseQuestionUseCase.execute(responseQuestion);
        }

        // Continue with the survey flow (e.g., go to the next screen)
        dispose(); // close current UI
        // Open the next UI, for example
    }

    private void goback(){
        dispose();
        LoginUiController.openUserUi();
    }
}
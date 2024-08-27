package com.surveymanagement.responseoption.infrastructure.responseoptionui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.surveymanagement.responseoption.application.FindResponseOptionByIdUseCase;
import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindCategoryCatalogByCodeUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;
import com.surveymanagement.categorycatalog.infrastructure.CategoryCatalogRepository;
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
import com.surveymanagement.responseoption.application.FindResponseOptionByQuestionUseCase;
import com.surveymanagement.responseoption.application.UpdateResponseOptionUseCase;
import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;
import com.surveymanagement.responseoption.infrastructure.ResponseOptionRepository;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class UpdateResponseOptionUI extends JFrame{
    private final UpdateResponseOptionUseCase updateResponseOptionUseCase;
        private final FindResponseOptionByIdUseCase findResponseOptionByCodeUseCase;
        private final ResponseOptionUI responseoptionUI;

        private JComboBox<String> responseoptionBox, surveyBox, chapterBox, questionBox, categorycatalogBox;
        private JTextField option_value, parentresponse_id, comment_response, option_text; //
        private JButton jButton1; // Save
        private JButton jButton2; // Save
        private JButton jButton3; // Go back
        private JButton jButton4; // Find
        private int surveyID, questionID, chapterID;
        private JCheckBox isCatalog, isParent; 
    

        public UpdateResponseOptionUI(UpdateResponseOptionUseCase updateResponseOptionUseCase, FindResponseOptionByIdUseCase findResponseOptionByCodeUseCase, ResponseOptionUI responseoptionUI) {
            this.updateResponseOptionUseCase = updateResponseOptionUseCase;
            this.findResponseOptionByCodeUseCase = findResponseOptionByCodeUseCase;
            this.responseoptionUI = responseoptionUI;
        }

        public void frmUpdateResponseOption() {
            initComponents();
            setVisible(true);
        }

        private void initComponents() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Update Response Option");
            setSize(600, 700);

            // Establecer el layout antes de agregar componentes
            setLayout(new GridBagLayout());

            JLabel jLabel1 = new JLabel("Update Response Option");
            jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

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
        
            surveyBox = new JComboBox<>();
            SurveyService surveyService = new SurveyRepository();
            FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
            List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
            for (Survey survey : surveys) {
                surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
            }
            addComponent(surveyBox, 1,2, 1);
    
            chapterBox = new JComboBox<>();
            surveyBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateChapterBox();
                }
            });
            addComponent(chapterBox, 2, 2, 1);
    
            questionBox = new JComboBox<>();
            chapterBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateQuestionBox();
                }
            });
            addComponent(questionBox, 3, 2, 1);
            
            responseoptionBox = new JComboBox<>();
            questionBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateResponseOptionBox();
                }
            });
            addComponent(responseoptionBox, 4, 2, 1);

            categorycatalogBox = new JComboBox<>();
        categorycatalogBox.setVisible(false);
        CategoryCatalogService categoryCatalogService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(categoryCatalogService);
        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog categoryCatalog : categorycatalogs){
            categorycatalogBox.addItem(String.valueOf(categoryCatalog.getId())+". "+ categoryCatalog.getName());
        }

            option_value = new JTextField();
            parentresponse_id = new JTextField();
            comment_response = new JTextField();
            option_text = new JTextField();

            jButton1 = new JButton("Reset");
            jButton2 = new JButton("Save");
            jButton3 = new JButton("Go back");
            jButton4 = new JButton("Find");

            jButton1.addActionListener(e -> resetFields());
            jButton2.addActionListener(e -> updateResponseOption());
            jButton3.addActionListener(e -> {
                dispose();
                responseoptionUI.showCrudOptions();
            });
            jButton4.addActionListener(e -> findResponseOption());

            // Añadir los componentes al contenedor
            addComponent(jLabel1, 0, 1, 2);
            addComponent(new JLabel("Survey:"), 1, 1, 1);
            addComponent(new JLabel("Chapter:"), 2, 1, 1);
            addComponent(new JLabel("Question:"), 3, 1, 1);
            addComponent(new JLabel("Response Option:"), 4, 1, 1);
            addComponent(jButton4, 5, 0, 3);
            

            addComponent(new JLabel("Value:"), 6, 1, 1);
            addComponent(option_value, 6, 2, 1);
            addComponent(new JLabel("Category Catalog:"), 7, 1, 1);
            addComponent(isCatalog, 7, 0, 1);
            addComponent(categorycatalogBox, 7, 2, 1);
            addComponent(new JLabel("Parent:"), 8, 1, 1);
            addComponent(isParent, 8, 0, 1);
            addComponent(parentresponse_id, 8, 2, 1);
            addComponent(new JLabel("comment response:"), 9, 1, 1);
            addComponent(comment_response, 9, 2, 1);
            addComponent(new JLabel("Text:"), 10, 1, 1);
            addComponent(option_text, 10, 2, 1);

            // Panel de botones
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(jButton2);
            buttonPanel.add(jButton1);
            buttonPanel.add(jButton3);
            addComponent(buttonPanel, 11, 0, 3);

            setLocationRelativeTo(null);

            hideComponents();
        }

        private void addComponent(Component component, int row, int col, int width) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = col;  // La columna en la que se agregará el componente
            gbc.gridy = row;  // La fila en la que se agregará el componente
            gbc.gridwidth = width;  // Número de celdas de ancho que ocupará el componente
            gbc.fill = GridBagConstraints.HORIZONTAL;  // El componente se estirará horizontalmente
            gbc.insets = new Insets(10, 10, 10, 10);  // Márgenes alrededor del componente
            gbc.anchor = GridBagConstraints.CENTER; // Centro del componente

            add(component, gbc);  // Añade el componente con las restricciones especificadas
        }

        private void updateResponseOption() {


            try {
                ResponseOption responseOption = new ResponseOption();
                if(isCatalog.isSelected()){

                    responseOption.setCategoryCatalogId(Integer.parseInt(TextBeforeDot(categorycatalogBox.getSelectedItem().toString())));
                } else {
                    responseOption.setCategoryCatalogId(0);
                }
                
                if(isParent.isSelected()){

                    responseOption.setParentResponseId(Integer.parseInt(parentresponse_id.getText().trim()));
                } else{
                    responseOption.setParentResponseId(0);
                }
                responseOption.setId(Integer.parseInt(TextBeforeDot(responseoptionBox.getSelectedItem().toString())));
                responseOption.setOptionValue(Integer.parseInt(option_value.getText().trim()));
                responseOption.setQuestionId(this.questionID);
                responseOption.setCommentResponse(comment_response.getText().trim());
                responseOption.setOptionText(option_text.getText().toString().trim());
                

                updateResponseOptionUseCase.execute(responseOption);
                JOptionPane.showMessageDialog(this, "ResponseOption updated successfully!");
                resetFields();
            } catch (Exception ex) {
                // Imprime el mensaje de error completo en la consola
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void findResponseOption() {
            int codeToUpdate = Integer.parseInt(TextBeforeDot(responseoptionBox.getSelectedItem().toString()));
            Optional<ResponseOption> responseoptionToUpdate = findResponseOptionByCodeUseCase.execute(codeToUpdate);

            if (responseoptionToUpdate.isPresent()) {
                CategoryCatalogService categoryCatalogService = new CategoryCatalogRepository();
                ResponseOption foundResponseOption = responseoptionToUpdate.get();
                FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase = new FindCategoryCatalogByCodeUseCase(categoryCatalogService);
                Optional<CategoryCatalog> findcategory = findCategoryCatalogByCodeUseCase.findCategoryCatalogByCode(foundResponseOption.getCategoryCatalogId());
                if (findcategory.isPresent()){
                    isCatalog.setSelected(true);
                    categorycatalogBox.setSelectedItem(String.valueOf(foundResponseOption.getCategoryCatalogId())+". "+ findcategory.get().getName() );
                    categorycatalogBox.setVisible(true);
                }
                if (foundResponseOption.getParentResponseId()>0){
                    isParent.setSelected(true);
                    System.out.println(foundResponseOption.getParentResponseId());
                    parentresponse_id.setEnabled(true);
                    
                    parentresponse_id.setText(String.valueOf(foundResponseOption.getParentResponseId()));
                } else{
                    parentresponse_id.setEnabled(false);
                    parentresponse_id.setText("");
                }
                System.out.println(foundResponseOption.getCategoryCatalogId());
                option_value.setText(String.valueOf(foundResponseOption.getOptionValue()));
                comment_response.setText(foundResponseOption.getCommentResponse());
                option_text.setText(foundResponseOption.getOptionText());
                showComponents();
                revalidate(); // Asegura que el layout se actualice
                repaint(); // Redibuja la ventana
            } else {
                JOptionPane.showMessageDialog(this, "ResponseOption not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void resetFields() {
            option_value.setText("");
            parentresponse_id.setText("");
            comment_response.setText("");
            categorycatalogBox.setVisible(false);
            option_text.setText("");
            surveyBox.setEditable(true);
            isParent.setSelected(false);
            isCatalog.setSelected(false);
            hideComponents();
        }

        private void hideComponents() {
            option_value.setVisible(false);
            parentresponse_id.setVisible(false);
            comment_response.setVisible(false);
            option_text.setVisible(false);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
        }

        private void showComponents() {
            option_value.setVisible(true);
            parentresponse_id.setVisible(true);
            comment_response.setVisible(true);
            option_text.setVisible(true);
            jButton1.setVisible(true);
            jButton2.setVisible(true);
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
        try {
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
            
        } catch (Exception e) {
            // TODO: handle exception
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


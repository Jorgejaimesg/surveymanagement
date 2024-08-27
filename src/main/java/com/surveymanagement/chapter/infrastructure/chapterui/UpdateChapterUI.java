package com.surveymanagement.chapter.infrastructure.chapterui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import com.surveymanagement.chapter.application.FindChapterByCodeUseCase;
import com.surveymanagement.chapter.application.FindChapterBySurveyUseCase;
import com.surveymanagement.chapter.application.UpdateChapterUseCase;
import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;
import com.surveymanagement.chapter.infrastructure.ChapterRepository;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class UpdateChapterUI extends JFrame{
    private final UpdateChapterUseCase updateChapterUseCase;
        private final FindChapterByCodeUseCase findChapterByCodeUseCase;
        private final ChapterUI chapterUI;
    
        private JComboBox<String> chapterBox, surveyBox, updateSurveyBox;
        private JTextField number, title; //
        private JButton jButton1; // Save
        private JButton jButton2; // Save
        private JButton jButton3; // Go back
        private JButton jButton4; // Find
        private int surveyID, chapterID;
        private String surveyName;
    
        public UpdateChapterUI(UpdateChapterUseCase updateChapterUseCase, FindChapterByCodeUseCase findChapterByCodeUseCase, ChapterUI chapterUI) {
            this.updateChapterUseCase = updateChapterUseCase;
            this.findChapterByCodeUseCase = findChapterByCodeUseCase;
            this.chapterUI = chapterUI;
        }
    
        public void frmUpdateChapter() {
            initComponents();
            setVisible(true);
        }
    
        private void initComponents() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Update Chapter");
            setSize(500, 500);
    
            // Establecer el layout antes de agregar componentes
            setLayout(new GridBagLayout());
    
            JLabel jLabel1 = new JLabel("Update Chapter");
            jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            
            surveyBox = new JComboBox<>();
            SurveyService surveyService = new SurveyRepository();
            FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
            List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
            for (Survey survey : surveys) {
                surveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
            }
            addComponent(surveyBox, 1, 1, 1);

            updateSurveyBox = new JComboBox<>();
            for (Survey survey : surveys) {
                updateSurveyBox.addItem(String.valueOf(survey.getId())+". " + survey.getName());
            }
            addComponent(updateSurveyBox, 4, 1, 1);
            
            chapterBox = new JComboBox<>();
            
            surveyBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateChapterBox();
                }
            });
            addComponent(chapterBox, 2, 1, 1);
    
            number = new JTextField();
            title = new JTextField();

    
            jButton1 = new JButton("Reset");
            jButton2 = new JButton("Save");
            jButton3 = new JButton("Go back");
            jButton4 = new JButton("Find");
    
            jButton1.addActionListener(e -> resetFields());
            jButton2.addActionListener(e -> updateChapter());
            jButton3.addActionListener(e -> {
                dispose();
                chapterUI.showCrudOptions();
            });
            jButton4.addActionListener(e -> findChapter());
    
            // Añadir los componentes al contenedor
            addComponent(jLabel1, 0, 0, 2);
            addComponent(new JLabel("Survey:"), 1, 0, 1);
            addComponent(new JLabel("Chapter:"), 2, 0, 1);
            addComponent(jButton4, 3, 0, 2);
            addComponent(new JLabel("Survey:"), 4, 0, 1);
            addComponent(new JLabel("Chapter Number:"), 5, 0, 1);
            addComponent(number, 5, 1, 1);
            addComponent(new JLabel("Chapter title:"), 6, 0, 1);
            addComponent(title, 6, 1, 1);
    
            // Panel de botones
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(jButton2);
            buttonPanel.add(jButton1);
            buttonPanel.add(jButton3);
            addComponent(buttonPanel, 7, 0, 2);
    
            setLocationRelativeTo(null);
    
            hideComponents();
        }
    
        private void addComponent(Component component, int row, int col, int width) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = col;  // La columna en la que se agregará el componente
            gbc.gridy = row;  // La fila en la que se agregará el componente
            gbc.gridwidth = width;  // Número de celdas de ancho que ocupará el componente
            gbc.fill = GridBagConstraints.HORIZONTAL;  // El componente se estirará horizontalmente
            gbc.insets = new Insets(5, 5, 5, 5);  // Márgenes alrededor del componente
            gbc.anchor = GridBagConstraints.CENTER; // Centro del componente
    
            add(component, gbc);  // Añade el componente con las restricciones especificadas
        }
    
        private void updateChapter() {
            try {
                Chapter chapter = new Chapter();
                chapter.setId(this.chapterID);
                chapter.setChapter_number(number.getText());
                chapter.setChapter_title(title.getText());
                chapter.setSurvey_id(Integer.parseInt(TextBeforeDot(updateSurveyBox.getSelectedItem().toString())));
                updateChapterUseCase.execute(chapter);
                JOptionPane.showMessageDialog(this, "Chapter updated successfully!");
                reloadComboBoxOptions();
                resetFields();
            } catch (Exception ex) {
                // Imprime el mensaje de error completo en la consola
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void findChapter() {
            int codeToUpdate = Integer.parseInt(TextBeforeDot(chapterBox.getSelectedItem().toString()));
            Optional<Chapter> chapterToUpdate = findChapterByCodeUseCase.findChapterByCode(codeToUpdate);
    
            if (chapterToUpdate.isPresent()) {
                Chapter foundChapter = chapterToUpdate.get();

                this.chapterID = foundChapter.getId();
                title.setText(foundChapter.getChapter_title());
                number.setText(foundChapter.getChapter_number());
                updateSurveyBox.setSelectedItem(String.valueOf(this.surveyID) + ". " + this.surveyName);
                surveyBox.setEditable(false);
                chapterBox.setEditable(false);
                showComponents();
                revalidate(); // Asegura que el layout se actualice
                repaint(); // Redibuja la ventana
            } else {
                JOptionPane.showMessageDialog(this, "Chapter not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void resetFields() {
            title.setText("");
            number.setText("");
            chapterBox.removeAllItems();
            surveyBox.setEditable(true);
            hideComponents();
        }
    
        private void hideComponents() {
            title.setVisible(false);
            number.setVisible(false);
            updateSurveyBox.setVisible(false);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
        }
    
        private void showComponents() {
            title.setVisible(true);
            number.setVisible(true);
            updateSurveyBox.setVisible(true);
            jButton1.setVisible(true);
            jButton2.setVisible(true);
        }
    
        private void reloadComboBoxOptions() {
            chapterBox.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
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
            FindSurveyByCodeUseCase findSurveyByCodeUseCase = new FindSurveyByCodeUseCase(surveyService);
            ChapterService chapterService = new ChapterRepository();
            FindChapterBySurveyUseCase findChapterBySurveyUseCase = new FindChapterBySurveyUseCase(chapterService);
            
            
            chapterBox.removeAllItems(); 
            this.surveyID = Integer.parseInt(TextBeforeDot(surveyBox.getSelectedItem().toString()));
            Optional<Survey> surveyFound = findSurveyByCodeUseCase.findSurveyByCode(surveyID);
            if (surveyFound.isPresent()){
                this.surveyName = surveyFound.get().getName();
                List<Chapter> Chapters = findChapterBySurveyUseCase.findChapterBySurvey(surveyID);
                for(Chapter Chapteritem : Chapters){
                    chapterBox.addItem(Chapteritem.getId()+". "+ Chapteritem.getChapter_title());
                };
        }}
}

package com.surveymanagement.survey.infrastructure.surveyui;

import java.awt.*;
import java.util.List;
import java.util.Optional;

import javax.swing.*;

import com.surveymanagement.survey.application.FindAllSurveyUseCase;
import com.surveymanagement.survey.application.FindSurveyByCodeUseCase;
import com.surveymanagement.survey.application.UpdateSurveyUseCase;
import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;
import com.surveymanagement.survey.infrastructure.SurveyRepository;

public class UpdateSurveyUI extends JFrame{
    private final UpdateSurveyUseCase updateSurveyUseCase;
        private final FindSurveyByCodeUseCase findSurveyByCodeUseCase;
        private final SurveyUI surveyUI;
    
        private JComboBox<String> surveyOptions;
        private JTextField name, description; // Survey Name, survey description
        private JButton jButton1; // Reset
        private JButton jButton2; // Save
        private JButton jButton3; // Go back
        private JButton jButton4; // Find
        private int foundId;
    
        public UpdateSurveyUI(UpdateSurveyUseCase updateSurveyUseCase, FindSurveyByCodeUseCase findSurveyByCodeUseCase, SurveyUI surveyUI) {
            this.updateSurveyUseCase = updateSurveyUseCase;
            this.findSurveyByCodeUseCase = findSurveyByCodeUseCase;
            this.surveyUI = surveyUI;
        }
    
        public void frmUpdateSurvey() {
            SurveyService surveyService = new SurveyRepository();
            FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
            initComponents(findAllSurveyUseCase);
            reloadComboBoxOptions();
            setVisible(true);
        }
    
        private void initComponents(FindAllSurveyUseCase findAllSurveyUseCase) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Update Survey");
            setSize(500, 500);
    
            // Establecer el layout antes de agregar componentes
            setLayout(new GridBagLayout());
    
            JLabel jLabel1 = new JLabel("Update Survey");
            jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            
            
            surveyOptions = new JComboBox<>();
            List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
            for (Survey survey : surveys) {
                surveyOptions.addItem(String.valueOf(survey.getId()) + ". " + survey.getName());
            }
    
            name = new JTextField();
            description = new JTextField();

    
            jButton1 = new JButton("Reset");
            jButton2 = new JButton("Save");
            jButton3 = new JButton("Go back");
            jButton4 = new JButton("Find");
    
            jButton1.addActionListener(e -> resetFields());
            jButton2.addActionListener(e -> updateSurvey());
            jButton3.addActionListener(e -> {
                dispose();
                surveyUI.showCrudOptions();
            });
            jButton4.addActionListener(e -> findSurvey());
    
            // Añadir los componentes al contenedor
            addComponent(jLabel1, 0, 0, 2);
            addComponent(new JLabel("Survey Code:"), 1, 0, 1);
            addComponent(surveyOptions, 1, 1, 1);
            addComponent(jButton4, 2, 0, 2);
            addComponent(new JLabel("Survey Name:"), 3, 0, 1);
            addComponent(name, 3, 1, 1);
            addComponent(new JLabel("Survey Description:"), 4, 0, 1);
            addComponent(description, 4, 1, 1);
    
            // Panel de botones
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(jButton2);
            buttonPanel.add(jButton1);
            buttonPanel.add(jButton3);
            addComponent(buttonPanel, 5, 0, 2);
    
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
    
        private void updateSurvey() {
            try {
                Survey survey = new Survey();
                survey.setId(foundId);
                survey.setName(name.getText());
                survey.setDescription(description.getText());
        
                updateSurveyUseCase.execute(survey);
                JOptionPane.showMessageDialog(this, "Survey updated successfully!");
                reloadComboBoxOptions();
                resetFields();
            } catch (Exception ex) {
                // Imprime el mensaje de error completo en la consola
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void findSurvey() {
            int codeToUpdate = Integer.parseInt(TextBeforeDot(surveyOptions.getSelectedItem().toString()));
            Optional<Survey> surveyToUpdate = findSurveyByCodeUseCase.findSurveyByCode(codeToUpdate);
    
            if (surveyToUpdate.isPresent()) {
                Survey foundSurvey = surveyToUpdate.get();
                foundId = foundSurvey.getId();
                description.setText(foundSurvey.getDescription());
                name.setText(foundSurvey.getName());
                surveyOptions.setEditable(false);
                showComponents();
                revalidate(); // Asegura que el layout se actualice
                repaint(); // Redibuja la ventana
            } else {
                JOptionPane.showMessageDialog(this, "Survey not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        private void resetFields() {
            name.setText("");
            description.setText("");
            surveyOptions.setEditable(true);
            hideComponents();
        }
    
        private void hideComponents() {
            name.setVisible(false);
            description.setVisible(false);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
        }
    
        private void showComponents() {
            name.setVisible(true);
            description.setVisible(true);
            jButton1.setVisible(true);
            jButton2.setVisible(true);
        }
    
        private void reloadComboBoxOptions() {
            surveyOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
        
            SurveyService surveyService = new SurveyRepository();
            FindAllSurveyUseCase findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
            
            List<Survey> surveys = findAllSurveyUseCase.findAllSurvey();
            for (Survey survey : surveys) {
                surveyOptions.addItem(String.valueOf(survey.getId())+ ". " + survey.getName()); // Añade los surveys actualizados al JComboBox
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
    
    }
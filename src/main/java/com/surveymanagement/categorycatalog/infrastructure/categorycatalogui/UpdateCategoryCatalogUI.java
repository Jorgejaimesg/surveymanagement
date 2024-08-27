package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import java.awt.*;
import java.util.Optional;
import java.util.List;
import javax.swing.*;

import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindCategoryCatalogByCodeUseCase;
import com.surveymanagement.categorycatalog.application.UpdateCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;
import com.surveymanagement.categorycatalog.infrastructure.CategoryCatalogRepository;

public class UpdateCategoryCatalogUI extends JFrame{
private final UpdateCategoryCatalogUseCase updateCategoryCatalogUseCase;
    private final FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase;
    private final CategoryCatalogUI categorycatalogUI;

    private JComboBox<String> categorycatalogOptions;
    private JTextField jTextField2; // CategoryCatalog Name
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int foundId;

    public UpdateCategoryCatalogUI(UpdateCategoryCatalogUseCase updateCategoryCatalogUseCase, FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase, CategoryCatalogUI categorycatalogUI) {
        this.updateCategoryCatalogUseCase = updateCategoryCatalogUseCase;
        this.findCategoryCatalogByCodeUseCase = findCategoryCatalogByCodeUseCase;
        this.categorycatalogUI = categorycatalogUI;
    }

    public void frmUpdateCategoryCatalog() {
        CategoryCatalogService categorycatalogService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(categorycatalogService);
        initComponents(findAllCategoryCatalogUseCase);
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents(FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Category Catalog");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update Category Catalog");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        categorycatalogOptions = new JComboBox<>();
        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog categorycatalog : categorycatalogs) {
            categorycatalogOptions.addItem(String.valueOf(categorycatalog.getId())+ ". "+ categorycatalog.getName());
        }

        jTextField2 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateCategoryCatalog());
        jButton3.addActionListener(e -> {
            dispose();
            categorycatalogUI.showCrudOptions();
        });
        jButton4.addActionListener(e -> findCategoryCatalog());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("CategoryCatalog Code:"), 1, 0, 1);
        addComponent(categorycatalogOptions, 1, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("CategoryCatalog Name:"), 3, 0, 1);
        addComponent(jTextField2, 3, 1, 1);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 4, 0, 2);

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

    private void updateCategoryCatalog() {
        try {
            CategoryCatalog categorycatalog = new CategoryCatalog();
            categorycatalog.setId(foundId);
            categorycatalog.setName(jTextField2.getText());
    
            updateCategoryCatalogUseCase.execute(categorycatalog);
            JOptionPane.showMessageDialog(this, "CategoryCatalog updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            // Imprime el mensaje de error completo en la consola
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findCategoryCatalog() {
        int codeToUpdate = Integer.parseInt(TextBeforeDot(categorycatalogOptions.getSelectedItem().toString()));
        Optional<CategoryCatalog> categorycatalogToUpdate = findCategoryCatalogByCodeUseCase.findCategoryCatalogByCode(codeToUpdate);
  
        if (categorycatalogToUpdate.isPresent()) {
            CategoryCatalog foundCategoryCatalog = categorycatalogToUpdate.get();
            foundId = foundCategoryCatalog.getId();
            jTextField2.setText(foundCategoryCatalog.getName());
            categorycatalogOptions.setEditable(false);
            showComponents();
            revalidate(); // Asegura que el layout se actualice
            repaint(); // Redibuja la ventana
        } else {
            JOptionPane.showMessageDialog(this, "CategoryCatalog not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField2.setText("");
        categorycatalogOptions.setEditable(true);
        hideComponents();
    }

    private void hideComponents() {
        jTextField2.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        jTextField2.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        categorycatalogOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        CategoryCatalogService categorycatalogService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(categorycatalogService);
        
        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog categorycatalog : categorycatalogs) {
            categorycatalogOptions.addItem(String.valueOf(categorycatalog.getId())+ ". "+ categorycatalog.getName()); // Añade los categorycatalogs actualizados al JComboBox
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

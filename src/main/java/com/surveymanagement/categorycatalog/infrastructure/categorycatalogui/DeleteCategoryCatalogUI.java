package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.surveymanagement.categorycatalog.application.DeleteCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindCategoryCatalogByCodeUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;
import com.surveymanagement.categorycatalog.infrastructure.CategoryCatalogRepository;


public class DeleteCategoryCatalogUI extends JFrame{
    private final DeleteCategoryCatalogUseCase deleteCategoryCatalogUseCase;
    private final CategoryCatalogUI categoryCatalogui;

    public DeleteCategoryCatalogUI(DeleteCategoryCatalogUseCase deleteCategoryCatalogUseCase,
            CategoryCatalogUI categoryCatalogui) {
        this.deleteCategoryCatalogUseCase = deleteCategoryCatalogUseCase;
        this.categoryCatalogui = categoryCatalogui;
    }

    private JComboBox<String> catalogs;
    private JTextArea resultArea;
    
    public void showDeleteCategoryCatalog(){

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Category Catalog");
        setSize(500, 500);
        
        CategoryCatalogService roleService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(roleService);
        initComponents(findAllCategoryCatalogUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Category catalog");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
    
        JLabel labelCategory = new JLabel("Catalog Category:");
        addComponent(labelCategory, 1, 0, 1);

        catalogs = new JComboBox<>();
        List<CategoryCatalog> categories = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog category : categories) {
            catalogs.addItem(category.getId() + ". " + category.getName());
        }
        addComponent(catalogs, 1, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteCategoryCatalog());
        addComponent(btnDelete, 2, 0, 2);

                resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            categoryCatalogui.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 2);
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

        private void deleteCategoryCatalog() {
        int categorycatalogCode = Integer.parseInt(TextBeforeDot(catalogs.getSelectedItem().toString()));
        CategoryCatalogService categoryCatalogService = new CategoryCatalogRepository();
        FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase = new FindCategoryCatalogByCodeUseCase(categoryCatalogService);
        Optional<CategoryCatalog> foundCategoryCatalog = findCategoryCatalogByCodeUseCase.findCategoryCatalogByCode(categorycatalogCode);
        
        if (foundCategoryCatalog.isPresent()) {
            deleteCategoryCatalogUseCase.execute(foundCategoryCatalog.get().getId());
            String message = String.format(
                "CategoryCatalog deleted successfully:\n\n" +
                "ID: %d\n" +
                "Name: %s\n",
                foundCategoryCatalog.get().getId(),
                foundCategoryCatalog.get().getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("CategoryCatalog deletion failed. CategoryCatalog not found.");
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

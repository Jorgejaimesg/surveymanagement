package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import java.util.List;
import java.util.Optional;
import java.awt.*;
import javax.swing.*;

import com.surveymanagement.categorycatalog.application.FindCategoryCatalogByCodeUseCase;
import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;
import com.surveymanagement.categorycatalog.infrastructure.CategoryCatalogRepository;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;

public class FindCategoryCatalogUI extends JFrame {
    private final FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase;
    private final CategoryCatalogUI categoryCatalogUI;
    public FindCategoryCatalogUI(FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase,
            CategoryCatalogUI categoryCatalogUI) {
        this.findCategoryCatalogByCodeUseCase = findCategoryCatalogByCodeUseCase;
        this.categoryCatalogUI = categoryCatalogUI;
    }
    
    private JComboBox<String> categoryCatalogOptions;
    private JTextArea resultArea;

    public void showFindCategoryCatalog() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Category Catalog");
        setSize(500, 500);

        CategoryCatalogService categorycatalogService = new CategoryCatalogRepository();
        FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase = new FindAllCategoryCatalogUseCase(categorycatalogService);
        initComponents(findAllCategoryCatalogUseCase);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Category Catalog");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("CategoryCatalog to find:");
        addComponent(lblId, 1, 0, 1);

        categoryCatalogOptions = new JComboBox<>();
        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        for (CategoryCatalog categorycatalog : categorycatalogs) {
            categoryCatalogOptions.addItem(String.valueOf(categorycatalog.getId()) + ". "+ categorycatalog.getName());
        }
        addComponent(categoryCatalogOptions, 1, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findCategoryCatalog());
        addComponent(btnFind, 2, 0, 2);

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
        btnClose.addActionListener(e -> {  dispose();
            categoryCatalogUI.showCrudOptions();
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
        private void findCategoryCatalog() {
        int categorycatalogId =Integer.parseInt(TextBeforeDot(categoryCatalogOptions.getSelectedItem().toString()));
        Optional<CategoryCatalog> categorycatalogOpt = findCategoryCatalogByCodeUseCase.findCategoryCatalogByCode(categorycatalogId);
        if (categorycatalogOpt.isPresent()) {
            CategoryCatalog categorycatalog = categorycatalogOpt.get();
            String message = String.format(
                "CategoryCatalog found:\n\n" +
                "ID: %s\n" +
                "CategoryCatalog Name: %s\n",
                categorycatalog.getId(),
                categorycatalog.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("CategoryCatalog not found!");
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

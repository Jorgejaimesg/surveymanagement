package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.surveymanagement.categorycatalog.application.CreateCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;

public class AddCategoryCatalogUI extends JFrame {
    private final CreateCategoryCatalogUseCase createCategoryCatalogUseCase;
    private final CategoryCatalogUI categoryCatalogUI;

    public AddCategoryCatalogUI(
            CreateCategoryCatalogUseCase createCategoryCatalogUseCase,
            CategoryCatalogUI categoryCatalogUI) {
        this.createCategoryCatalogUseCase = createCategoryCatalogUseCase;
        this.categoryCatalogUI = categoryCatalogUI;
    }
    private JTextField name;
    private JButton create, goback, reset ;
    
    public void frmRegCategoryCatalog(){
        initComponents();
        setVisible(true);
    }

    private void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Category Catalog");
        setSize(500, 500);

        JLabel title = new JLabel("Create Category Catalog");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        name = new JTextField();

        reset = new JButton("Reset");
        create = new JButton("Create");
        goback = new JButton("Go back");

        reset.addActionListener(e -> resetFields());
        create.addActionListener(e -> saveCategoyCatalog());
        goback.addActionListener(e -> {
            dispose();
            categoryCatalogUI.showCrudOptions(); 
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(title, 0, 0, 2);
        addComponent(new JLabel("Catalog Name:"), 1, 0, 1);
        addComponent(name, 1, 1, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(reset);
        buttonPanel.add(goback);
        gbc.gridx = 0;
        gbc.gridy = 2;
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

    private void saveCategoyCatalog() {
        try {
            CategoryCatalog categorycatalog = new CategoryCatalog();
            categorycatalog.setName(name.getText());


            createCategoryCatalogUseCase.execute(categorycatalog); // Corrected from "customer" to "role"
            JOptionPane.showMessageDialog(this, "CategoryCatalog added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        name.setText("");
    }
}


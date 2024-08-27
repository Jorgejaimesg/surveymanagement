package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.surveymanagement.categorycatalog.application.CreateCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.DeleteCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.application.FindCategoryCatalogByCodeUseCase;
import com.surveymanagement.categorycatalog.application.UpdateCategoryCatalogUseCase;
import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;

public class CategoryCatalogUI {
    private final CreateCategoryCatalogUseCase createCategoryCatalogUseCase;
    private final DeleteCategoryCatalogUseCase deleteCategoryCatalogUseCase;
    private final FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase;
    private final FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase;
    private final UpdateCategoryCatalogUseCase updateCategoryCatalogUseCase;
    private JFrame frame;

    public CategoryCatalogUI(
            CreateCategoryCatalogUseCase createCategoryCatalogUseCase,
            DeleteCategoryCatalogUseCase deleteCategoryCatalogUseCase,
            FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase,
            FindCategoryCatalogByCodeUseCase findCategoryCatalogByCodeUseCase,
            UpdateCategoryCatalogUseCase updateCategoryCatalogUseCase) {
        this.createCategoryCatalogUseCase = createCategoryCatalogUseCase;
        this.deleteCategoryCatalogUseCase = deleteCategoryCatalogUseCase;
        this.findAllCategoryCatalogUseCase = findAllCategoryCatalogUseCase;
        this.findCategoryCatalogByCodeUseCase = findCategoryCatalogByCodeUseCase;
        this.updateCategoryCatalogUseCase = updateCategoryCatalogUseCase;
    }

    public void showCrudOptions(){

        frame = new JFrame("Category Catalogs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
    
                // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Category Catalogs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo común para los botones
        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Botón Create CategoryCatalog
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            AddCategoryCatalogUI addcategorycatalogUi = new AddCategoryCatalogUI(createCategoryCatalogUseCase, this);
            addcategorycatalogUi.frmRegCategoryCatalog();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateCategoryCatalogUI updateCategoryCatalogUi = new UpdateCategoryCatalogUI(updateCategoryCatalogUseCase, findCategoryCatalogByCodeUseCase, this);
            updateCategoryCatalogUi.frmUpdateCategoryCatalog();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindCategoryCatalogUI findCategoryCatalogUI = new FindCategoryCatalogUI(findCategoryCatalogByCodeUseCase, this);
            findCategoryCatalogUI.showFindCategoryCatalog();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteCategoryCatalogUI deleteCustomerUI = new DeleteCategoryCatalogUI(deleteCategoryCatalogUseCase, this);
            deleteCustomerUI.showDeleteCategoryCatalog();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllCategoryCatalogUI findAllCategoryCatalogUi = new FindAllCategoryCatalogUI(findAllCategoryCatalogUseCase, this);
            findAllCategoryCatalogUi.showAllCategoryCatalogs();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Back to Main Menu", buttonSize, buttonFont);
        btnBackToMain.addActionListener(e -> {
            frame.dispose(); 
            LoginUiController.createAndShowMainUI(); 
        });
        buttonPanel.add(btnBackToMain);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));



        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    }

    


package com.surveymanagement.chapter.infrastructure.chapterui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.surveymanagement.chapter.application.CreateChapterUseCase;
import com.surveymanagement.chapter.application.DeleteChapterUseCase;
import com.surveymanagement.chapter.application.FindChapterByCodeUseCase;
import com.surveymanagement.chapter.application.FindAllChapterUseCase;
import com.surveymanagement.chapter.application.UpdateChapterUseCase;
import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;


public class ChapterUI {
    private final CreateChapterUseCase createChapterUseCase;
    private final DeleteChapterUseCase deleteChapterUseCase;
    private final FindChapterByCodeUseCase findChapterByCodeUseCase;
    private final FindAllChapterUseCase findAllChapterUseCase;
    private final UpdateChapterUseCase updateChapterUseCase;
    private JFrame frame;

    public ChapterUI(
            CreateChapterUseCase createChapterUseCase,
            DeleteChapterUseCase deleteChapterUseCase,
            FindChapterByCodeUseCase findChapterByCodeUseCase,
            UpdateChapterUseCase updateChapterUseCase,
            FindAllChapterUseCase findAllChapterUseCase) {
        this.createChapterUseCase = createChapterUseCase;
        this.deleteChapterUseCase = deleteChapterUseCase;
        this.findChapterByCodeUseCase = findChapterByCodeUseCase;
        this.findAllChapterUseCase = findAllChapterUseCase;
        this.updateChapterUseCase = updateChapterUseCase;
    }

    public void showCrudOptions(){

        frame = new JFrame("Chapter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
    
                // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Chapter");
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

        // Botón Create Chapter
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            AddChapterUI addchapterUi = new AddChapterUI(createChapterUseCase, this);
            addchapterUi.frmRegChapter();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateChapterUI updateChapterUi = new UpdateChapterUI(updateChapterUseCase, findChapterByCodeUseCase, this);
            updateChapterUi.frmUpdateChapter();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindChapterUI findChapterUI = new FindChapterUI(findChapterByCodeUseCase, this);
            findChapterUI.showFindChapter();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteChapterUI deleteCustomerUI = new DeleteChapterUI(deleteChapterUseCase, this);
            deleteCustomerUI.showDeleteChapter();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllChapterUI findAllChapterUi = new FindAllChapterUI(findAllChapterUseCase, this);
            findAllChapterUi.showAllChapters();
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
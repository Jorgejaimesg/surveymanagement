package com.surveymanagement.question.infrastructure.questionui;

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

import com.surveymanagement.login.infrastructure.loginUi.LoginUiController;
import com.surveymanagement.question.application.CreateQuestionUseCase;
import com.surveymanagement.question.application.DeleteQuestionUseCase;
import com.surveymanagement.question.application.FindAllQuestionUseCase;
import com.surveymanagement.question.application.FindQuestionByCodeUseCase;
import com.surveymanagement.question.application.UpdateQuestionUseCase;


public class QuestionUI {

private final CreateQuestionUseCase createQuestionUseCase;
private final DeleteQuestionUseCase deleteQuestionUseCase;
private final FindQuestionByCodeUseCase findQuestionByCodeUseCase;
private final FindAllQuestionUseCase findAllQuestionUseCase;
private final UpdateQuestionUseCase updateQuestionUseCase;
private JFrame frame;

public QuestionUI(
        CreateQuestionUseCase createQuestionUseCase,
        DeleteQuestionUseCase deleteQuestionUseCase,
        FindQuestionByCodeUseCase findQuestionByCodeUseCase,
        UpdateQuestionUseCase updateQuestionUseCase,
        FindAllQuestionUseCase findAllQuestionUseCase) {
    this.createQuestionUseCase = createQuestionUseCase;
    this.deleteQuestionUseCase = deleteQuestionUseCase;
    this.findQuestionByCodeUseCase = findQuestionByCodeUseCase;
    this.findAllQuestionUseCase = findAllQuestionUseCase;
    this.updateQuestionUseCase = updateQuestionUseCase;
}

public void showCrudOptions(){

    frame = new JFrame("Question");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 500);
    frame.setLocationRelativeTo(null);

            // Crear un panel principal con BoxLayout
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Añadir título grande
    JLabel titleLabel = new JLabel("Question");
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

    // Botón Create Question
    JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
    btnCreate.addActionListener(e -> {
        AddQuestionUI addquestionUi = new AddQuestionUI(createQuestionUseCase, this);
        addquestionUi.frmRegQuestion();
        frame.setVisible(false);
    });
    buttonPanel.add(btnCreate);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
    btnUpdate.addActionListener(e -> {
        UpdateQuestionUI updateQuestionUi = new UpdateQuestionUI(updateQuestionUseCase, findQuestionByCodeUseCase, this);
        updateQuestionUi.frmUpdateQuestion();
        frame.setVisible(false);
    });
    buttonPanel.add(btnUpdate);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
    btnFind.addActionListener(e -> {
        FindQuestionUI findQuestionUI = new FindQuestionUI(findQuestionByCodeUseCase, this);
        findQuestionUI.showFindQuestion();
        frame.setVisible(false);
    });
    buttonPanel.add(btnFind);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
    btnDelete.addActionListener(e -> {
        DeleteQuestionUI deleteCustomerUI = new DeleteQuestionUI(deleteQuestionUseCase, this);
        deleteCustomerUI.showDeleteQuestion();
        frame.setVisible(false);
    });
    buttonPanel.add(btnDelete);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
    btnFindAll.addActionListener(e -> {
        FindAllQuestionUI findAllQuestionUi = new FindAllQuestionUI(findAllQuestionUseCase, this);
        findAllQuestionUi.showAllQuestions();
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

package com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui;

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
import com.surveymanagement.subresponseoption.application.CreateSubResponseOptionUseCase;
import com.surveymanagement.subresponseoption.application.FindSubResponseOptionByIdUseCase;
import com.surveymanagement.subresponseoption.application.DeleteSubResponseOptionUseCase;
import com.surveymanagement.subresponseoption.application.UpdateSubResponseOptionUseCase;
// import com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui.DeleteSubResponseOptionUi;
// import com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui.FindSubResponseOptionByIdUi;
// import com.surveymanagement.subresponseoption.infrastructure.subresponseoptionui.UpdateSubResponseOptionUi;

public class SubResponseOptionUi {
    private final CreateSubResponseOptionUseCase createSubResponseOptionUseCase;
    private final FindSubResponseOptionByIdUseCase findSubResponseOptionByIdUseCase;
    private final UpdateSubResponseOptionUseCase updateSubResponseOptionUseCase;
    private final DeleteSubResponseOptionUseCase deleteSubResponseOptionUseCase;
    private JFrame frame;

    public SubResponseOptionUi(CreateSubResponseOptionUseCase createSubResponseOptionUseCase,
            FindSubResponseOptionByIdUseCase findSubResponseOptionByIdUseCase,
            UpdateSubResponseOptionUseCase updateSubResponseOptionUseCase,
            DeleteSubResponseOptionUseCase deleteSubResponseOptionUseCase) {
        this.createSubResponseOptionUseCase = createSubResponseOptionUseCase;
        this.findSubResponseOptionByIdUseCase = findSubResponseOptionByIdUseCase;
        this.updateSubResponseOptionUseCase = updateSubResponseOptionUseCase;
        this.deleteSubResponseOptionUseCase = deleteSubResponseOptionUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Subresponse Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Subresponse Options");
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

        // Botón Create SubResponseOption
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateSubResponseOptionUi subResponseOptionUi = new CreateSubResponseOptionUi(createSubResponseOptionUseCase, this);
            subResponseOptionUi.frmRegSubResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // JButton btnUpdate = createStyledButton("Update SubResponseOption", buttonSize, buttonFont);
        // btnUpdate.addActionListener(e -> {
        //     UpdateSubResponseOptionUi updateSubResponseOptionUi = new UpdateSubResponseOptionUi(updateSubResponseOptionUseCase, findSubResponseOptionByNameUseCase, this);
        //     updateSubResponseOptionUi.frmUpdateSubResponseOption();
        //     frame.setVisible(false);
        // });
        // buttonPanel.add(btnUpdate);
        // buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindSubResponseOptionUI findSubResponseOptionUi = new FindSubResponseOptionUI(findSubResponseOptionByIdUseCase, this);
            findSubResponseOptionUi.showFindSubResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteSubResponseOptionUi deleteCustomerUi = new DeleteSubResponseOptionUi(deleteSubResponseOptionUseCase, this);
            deleteCustomerUi.showDeleteSubResponseOption();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Back to Main Menu", buttonSize, buttonFont);
        btnBackToMain.addActionListener(e -> {
            frame.dispose(); 
            LoginUiController.createAndShowMainUI(); 
        });
        buttonPanel.add(btnBackToMain);

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

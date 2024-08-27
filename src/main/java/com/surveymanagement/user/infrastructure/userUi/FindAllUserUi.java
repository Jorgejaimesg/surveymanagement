package com.surveymanagement.user.infrastructure.userUi;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.surveymanagement.user.application.FindAllUserUseCase;
import com.surveymanagement.user.domain.entity.User;

import java.awt.*;
import java.util.List;


public class FindAllUserUi {
    private final FindAllUserUseCase findAllUserUseCase;
    private final UserUiController userUiController;
    private JFrame frame;

    public FindAllUserUi(FindAllUserUseCase findAllUserUseCase, UserUiController userUiController) {
        this.findAllUserUseCase = findAllUserUseCase;
        this.userUiController = userUiController;
    }

    public void showAllUsers() {
        frame = new JFrame("All Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable userTable = createUserTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            userUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createUserTable() {
        String[] columnNames = {"Id","IsActive","Username", "Password"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<User> users = findAllUserUseCase.execute();
        if (!users.isEmpty()) {
            for (User user : users) {
                Object[] rowData = {
                    user.getId(),
                    user.isEnabled(),
                    user.getUsername(),
                    user.getPassword()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No users found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}
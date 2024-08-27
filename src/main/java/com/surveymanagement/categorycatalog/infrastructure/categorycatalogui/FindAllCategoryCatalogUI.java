package com.surveymanagement.categorycatalog.infrastructure.categorycatalogui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.surveymanagement.categorycatalog.application.FindAllCategoryCatalogUseCase;
import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;

public class FindAllCategoryCatalogUI {
private final FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase;
    private final CategoryCatalogUI categorycatalogui;
    private JFrame frame;

    public FindAllCategoryCatalogUI(FindAllCategoryCatalogUseCase findAllCategoryCatalogUseCase, CategoryCatalogUI categorycatalogUI) {
        this.findAllCategoryCatalogUseCase = findAllCategoryCatalogUseCase;
        this.categorycatalogui = categorycatalogUI;
    }

    public void showAllCategoryCatalogs() {
        frame = new JFrame("All CategoryCatalogs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All CategoryCatalogs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable categorycatalogTable = createCategoryCatalogTable();
        JScrollPane scrollPane = new JScrollPane(categorycatalogTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            categorycatalogui.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createCategoryCatalogTable() {
        String[] columnNames = {"ID","Update at","Created at", "Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<CategoryCatalog> categorycatalogs = findAllCategoryCatalogUseCase.findAllCategoryCatalog();
        if (!categorycatalogs.isEmpty()) {
            for (CategoryCatalog categorycatalog : categorycatalogs) {
                Object[] rowData = {
                    categorycatalog.getId(),
                    categorycatalog.getUpdated_at(),
                    categorycatalog.getCreated_at(),
                    categorycatalog.getName()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No categorycatalogs found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}

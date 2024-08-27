package com.surveymanagement.categorycatalog.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class CategoryCatalogRepository implements  CategoryCatalogService{
    private Connection connection;
    public CategoryCatalogRepository(){
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("configdb.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void createCategoryCatalog(CategoryCatalog categorycatalog) {
        try {
            String query = "INSERT INTO categories_catalog (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, categorycatalog.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateCategoryCatalog(CategoryCatalog categorycatalog) {
        String query = "UPDATE categories_catalog SET name = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, categorycatalog.getName());
            ps.setInt(2, categorycatalog.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    @Override
    public CategoryCatalog deleteCategoryCatalog(int id) {
        String query = "DELETE FROM categories_catalog WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Optional<CategoryCatalog> findCategoryCatalogByName(String name) {
        String query = "SELECT id, created_at, updated_at, name FROM categories_catalog WHERE name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CategoryCatalog categorycatalog = new CategoryCatalog(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("name"));
                    return Optional.of(categorycatalog);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<CategoryCatalog> findCategoryCatalogByCode(int id) {
        String query = "SELECT id, created_at, updated_at, name FROM categories_catalog WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CategoryCatalog categorycatalog = new CategoryCatalog(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("name"));
                    return Optional.of(categorycatalog);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public List<CategoryCatalog> findAllCategoryCatalog() {
    List<CategoryCatalog> categorycatalogs = new ArrayList<>();
        String query = "SELECT id, created_at, updated_at, name FROM categories_catalog";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryCatalog categorycatalog = new CategoryCatalog(
                        rs.getInt("id"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("name"));
                        categorycatalogs.add(categorycatalog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorycatalogs;
    
    }
}



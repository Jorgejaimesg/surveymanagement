package com.surveymanagement.survey.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class SurveyRepository implements  SurveyService{
    private Connection connection;
    public SurveyRepository(){
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
    public void createSurvey(Survey survey) {
        try {
            String query = "INSERT INTO surveys (description, name) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, survey.getDescription());
            ps.setString(2, survey.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateSurvey(Survey survey) {
        String query = "UPDATE surveys SET name = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, survey.getName());
            ps.setString(2, survey.getDescription());
            ps.setInt(3, survey.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    @Override
    public Survey deleteSurvey(int id) {
        String query = "DELETE FROM surveys WHERE id = ?";
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
    public Optional<Survey> findSurveyByName(String name) {
        String query = "SELECT id , created_at, updated_at, name, description FROM surveys WHERE name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Survey survey = new Survey(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("name"),
                            rs.getString("description"));
                    return Optional.of(survey);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Survey> findSurveyByCode(int id) {
        String query = "SELECT id, created_at, updated_at, name, description FROM surveys WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Survey survey = new Survey(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("description"),
                            rs.getString("name"));
                    return Optional.of(survey);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public List<Survey> findAllSurvey() {
    List<Survey> surveys = new ArrayList<>();
        String query = "SELECT id, created_at, updated_at, description, name FROM surveys";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Survey survey = new Survey(
                        rs.getInt("id"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("description"),
                        rs.getString("name"));
                        surveys.add(survey);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surveys;
    
    }
}



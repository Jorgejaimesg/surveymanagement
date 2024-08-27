package com.surveymanagement.subresponseoption.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

public class SubResponseOptionRepository implements SubResponseOptionService {
    private Connection connection;

    public SubResponseOptionRepository() {
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
    public void createSubResponseOption(SubResponseOption subResponseOption) {
        try {
            String query = "INSERT INTO subresponse_options (subresponse_number,responseoptions_id,subresponse_text) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, subResponseOption.getSubResponseNumber());
            ps.setInt(2, subResponseOption.getResponseOptionId());
            ps.setString(3, subResponseOption.getSubResponseText());
          

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SubResponseOption added successfully!");
            } else {
                System.out.println("SubResponseOption addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubResponseOption(SubResponseOption subResponseOption) {
        String query = "UPDATE subresponse_options SET subresponse_number = ?,responseoptions_id = ?,subresponse_text = ?  WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, subResponseOption.getSubResponseNumber());
            ps.setInt(2, subResponseOption.getResponseOptionId());
            ps.setString(3, subResponseOption.getSubResponseText());
            ps.setInt(4, subResponseOption.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SubResponseOption updated successfully!");
            } else {  
                System.out.println("SubResponseOption update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SubResponseOption deleteSubResponseOption(int subResponseOptionId) {
        SubResponseOption subResponseOption = null;
        String selectQuery = "SELECT * FROM subresponse_options WHERE id = ?";
        String deleteQuery = "DELETE FROM subresponse_options WHERE id = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the role
            selectPs.setInt(1, subResponseOptionId);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    subResponseOption = new SubResponseOption(
                        rs.getInt("id"),
                        rs.getInt("subresponse_number"),
                        rs.getString("created_at"),
                        rs.getInt("responseoptions_id"),
                        rs.getString("updated_at"),
                        rs.getString("subresponse_text") 
                        
                    );
                }
            }

            // If role exists, delete it
            if (subResponseOption != null) {
                deletePs.setInt(1, subResponseOptionId);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("SubResponseOption deleted successfully!");
                    return subResponseOption;
                }
            }

            System.out.println("SubResponseOption deletion failed. Role not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<SubResponseOption> findSubResponseOptionById(int subResponseOptionId) {
        String query = "SELECT * FROM subresponse_options WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, subResponseOptionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SubResponseOption responseOption = new SubResponseOption(
                        rs.getInt("id"),
                        rs.getInt("subresponse_number"),
                        rs.getString("created_at"),
                        rs.getInt("responseoptions_id"),
                        rs.getString("updated_at"),
                        rs.getString("subresponse_text") 
                    );
                    return Optional.of(responseOption);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<SubResponseOption> findSubResponseOptionByResponseOption(int responseOptionId) {
        List<SubResponseOption> subResponseOptionIds = new ArrayList<>();
        String query = "SELECT * FROM subresponse_options WHERE responseoptions_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, responseOptionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubResponseOption subResponseOptionId = new SubResponseOption(
                        rs.getInt("id"),
                        rs.getInt("subresponse_number"),
                        rs.getString("created_at"),
                        responseOptionId,
                        rs.getString("updated_at"),
                        rs.getString("subresponse_text"));
                        subResponseOptionIds.add(subResponseOptionId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subResponseOptionIds;
    }

}

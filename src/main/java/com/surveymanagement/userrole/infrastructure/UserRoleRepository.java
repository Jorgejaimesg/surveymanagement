package com.surveymanagement.userrole.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.userrole.domain.entity.UserRole;
import com.surveymanagement.userrole.domain.service.UserRoleService;

public class UserRoleRepository implements UserRoleService {
    private Connection connection;

    public UserRoleRepository() {
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
    public void createUserRole(UserRole userRole) {
        try {
            String query = "INSERT INTO users_Roles (role_id,user_id) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userRole.getRoleId());
            ps.setInt(2, userRole.getUserId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UserRole added successfully!");
            } else {
                System.out.println("UserRole addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserRole(UserRole userRole) {
        String query = "UPDATE Users_Roles SET role_Id = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userRole.getRoleId());
            ps.setInt(2, userRole.getUserId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UserRole updated successfully!");
            } else {  
                System.out.println("UserRole update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserRole> findUserRoleById(String userId){
            String query = "SELECT role_id,user_id FROM Users_Roles WHERE user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, userId);
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        UserRole UserRole = new UserRole(
                            rs.getInt("role_id"),
                            rs.getInt("user_id")
                        );
                        return Optional.of(UserRole);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }

    @Override
    public UserRole deleteUserRole(String userId) {
        UserRole userRole = null;
        String selectQuery = "SELECT * FROM Users_Roles WHERE user_id = ?";
        String deleteQuery = "DELETE FROM Users_Roles WHERE user_id = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the userUserRole
            
            selectPs.setString(1, userId);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    userRole = new UserRole(
                        rs.getInt("role_id"),
                        rs.getInt("user_id")
                    );
                }
            }

            // If userUserRole exists, delete it
            if (userRole != null) {
            
                deletePs.setString(1, userId);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("UserRole deleted successfully!");
                    return userRole;
                }
            }

            System.out.println("UserRole deletion failed. UserRole not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserRole> findAllUserRole() {
        List<UserRole> userRoles = new ArrayList<>();
        String query = "SELECT r.name, u.username FROM Users_Roles ur JOIN users u ON u.id = ur.user_id JOIN roles r ON r.id = ur.role_id " ;
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                UserRole userRole = new UserRole(
                    rs.getInt("id"),
                    rs.getInt("name")
                );
                userRoles.add(userRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userRoles;
    }


}



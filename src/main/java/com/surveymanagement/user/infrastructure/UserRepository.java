package com.surveymanagement.user.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.user.domain.entity.User;
import com.surveymanagement.user.domain.service.UserService;

public class UserRepository implements UserService {
    private Connection connection;

    public UserRepository() {
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
    public void createUser(User user) {
        try {
            String query = "INSERT INTO users (enabled,username,password) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBoolean(1, user.isEnabled());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("User addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET enabled = ? , username = ?, password = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setBoolean(1, user.isEnabled());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully!");
                
            } else {  
                System.out.println("User update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findUserById(String userName) {
            String query = "SELECT id,enabled,username,password FROM users WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User(
                            rs.getInt("id"),
                            rs.getBoolean("enabled"),
                            rs.getString("username"),
                            rs.getString("password")
                        );
                        return Optional.of(user);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }

    @Override
    public User deleteUser(String userName) {
        User user = null;
        String selectQuery = "SELECT * FROM users WHERE id = ?";
        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the user
            selectPs.setString(1, userName);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("id"),
                        rs.getBoolean("enabled"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }

            // If user exists, delete it
            if (user != null) {
                deletePs.setString(1, userName);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User deleted successfully!");
                    return user;
                }
            }

            System.out.println("User deletion failed. User not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id,enabled,username,password FROM users";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getBoolean("enabled"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
}



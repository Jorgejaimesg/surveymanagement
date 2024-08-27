package com.surveymanagement.login.infrastructure;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import javax.swing.JOptionPane;

import com.surveymanagement.login.domain.entity.Login;
import com.surveymanagement.login.domain.service.LoginService;

public class LoginRepository implements LoginService {
    private Connection connection;
    public LoginRepository() {
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
    public Optional<Login> checkUserRole(String username, String password) {
    String query = "SELECT u.id, u.username, u.password, r.name AS role_name " +
                   "FROM users u " +
                   "JOIN users_roles ur ON u.id = ur.user_id " +
                   "JOIN roles r ON ur.role_id = r.id " +
                   "WHERE u.username = ? AND u.password = ?";
    
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, username);
        ps.setString(2, password); 
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String roleName = rs.getString("role_name");
                if ("Admin".equalsIgnoreCase(roleName) || "User".equalsIgnoreCase(roleName)) {
                    Login login = new Login(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        roleName
                    );
                    return Optional.of(login);
                } else {
                    JOptionPane.showMessageDialog(null, "Unauthorized role or not Role Assign", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Optional.empty();
}
}

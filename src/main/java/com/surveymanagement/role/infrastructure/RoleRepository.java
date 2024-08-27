package com.surveymanagement.role.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.role.domain.entity.Role;
import com.surveymanagement.role.domain.service.RoleService;

public class RoleRepository implements RoleService {
    private Connection connection;

    public RoleRepository() {
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
    public void createRole(Role role) {
        try {
            String query = "INSERT INTO roles (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, role.getName());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Role added successfully!");
            } else {
                System.out.println("Role addition failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRole(Role role) {
        String query = "UPDATE roles SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, role.getName());
            ps.setInt(2, role.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Role updated successfully!");
            } else {  
                System.out.println("Role update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
            String query = "SELECT id,name FROM roles WHERE name = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, roleName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Role role = new Role(
                            rs.getInt("id"),
                            rs.getString("name")
                        );
                        return Optional.of(role);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }

    @Override
    public Optional<Role> findRoleById(String roleId) {
            String query = "SELECT id,name FROM roles WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, roleId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Role role = new Role(
                            rs.getInt("id"),
                            rs.getString("name")
                        );
                        return Optional.of(role);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }


    @Override
    public Role deleteRole(String roleName) {
        Role role = null;
        String selectQuery = "SELECT * FROM roles WHERE name = ?";
        String deleteQuery = "DELETE FROM roles WHERE name = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
             PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the role
            selectPs.setString(1, roleName);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    role = new Role(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }

            // If role exists, delete it
            if (role != null) {
                deletePs.setString(1, roleName);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Role deleted successfully!");
                    return role;
                }
            }

            System.out.println("Role deletion failed. Role not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> findAllRole() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT id,name FROM roles";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Role role = new Role(
                    rs.getInt("id"),
                    rs.getString("name")
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return roles;
    }
}



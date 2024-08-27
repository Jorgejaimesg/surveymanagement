package com.surveymanagement.login.domain.entity;

public class Login {
    private int id;
    private String username;
    private String password;
    private String rolename;
   
    public Login() {
    }

    public Login(int id, String username, String password,String rolename) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    

    
}

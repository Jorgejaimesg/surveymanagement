package com.surveymanagement.survey.domain.entity;

public class Survey {
    private int id;
    private String created_at;
    private String updated_at;
    private String description;
    private String name;
    public Survey() {
    }
    public Survey(int id, String description, String name) {
        this.id = id;
        this.description = description;
        this.name = name;
    }
    public Survey(int id, String created_at, String updated_at, String description, String name) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.description = description;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}

package com.surveymanagement.chapter.domain.entity;

public class Chapter {
    private int id;
    private String created_at;
    private int survey_id;
    private String updated_at;
    private String chapter_number;
    private String chapter_title;
    public Chapter() {
    }
    public Chapter(int id, int survey_id, String chapter_number, String chapter_title) {
        this.id = id;
        this.survey_id = survey_id;
        this.chapter_number = chapter_number;
        this.chapter_title = chapter_title;
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
    public int getSurvey_id() {
        return survey_id;
    }
    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public String getChapter_number() {
        return chapter_number;
    }
    public void setChapter_number(String chapter_number) {
        this.chapter_number = chapter_number;
    }
    public String getChapter_title() {
        return chapter_title;
    }
    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }
    public Chapter(int id, String created_at, int survey_id, String updated_at, String chapter_number,
            String chapter_title) {
        this.id = id;
        this.created_at = created_at;
        this.survey_id = survey_id;
        this.updated_at = updated_at;
        this.chapter_number = chapter_number;
        this.chapter_title = chapter_title;
    }
    
}

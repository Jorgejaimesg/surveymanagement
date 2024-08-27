package com.surveymanagement.question.domain.entity;

public class Question {
    private int id;
    private int chapter_id;
    private String created_at;
    private String updated_at;
    private String question_number;
    private String response_type;
    private String comment_question;
    private String question_text;
    public Question() {
    }
    public Question(int id, int chapter_id, String question_number, String response_type, String comment_question,
            String question_text) {
        this.id = id;
        this.chapter_id = chapter_id;
        this.question_number = question_number;
        this.response_type = response_type;
        this.comment_question = comment_question;
        this.question_text = question_text;
    }
    public Question(int id, int chapter_id, String created_at, String updated_at, String question_number,
            String response_type, String comment_question, String question_text) {
        this.id = id;
        this.chapter_id = chapter_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.question_number = question_number;
        this.response_type = response_type;
        this.comment_question = comment_question;
        this.question_text = question_text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getChapter_id() {
        return chapter_id;
    }
    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
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
    public String getQuestion_number() {
        return question_number;
    }
    public void setQuestion_number(String question_number) {
        this.question_number = question_number;
    }
    public String getResponse_type() {
        return response_type;
    }
    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }
    public String getComment_question() {
        return comment_question;
    }
    public void setComment_question(String comment_question) {
        this.comment_question = comment_question;
    }
    public String getQuestion_text() {
        return question_text;
    }
    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }
}

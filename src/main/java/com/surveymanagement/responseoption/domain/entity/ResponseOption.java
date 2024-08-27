package com.surveymanagement.responseoption.domain.entity;

public class ResponseOption {
    private int id;
    private int optionValue;
    private int categoryCatalogId;
    private String createdAt;
    private int parentResponseId;
    private int questionId;
    private String updateAt;
    private String commentResponse;
    private String optionText;

    public ResponseOption() {
    }

    public ResponseOption(int id, int optionValue, int categoryCatalogId, String createdAt, int parentResponseId,
            int questionId, String updateAt, String commentResponse, String optionText) {
        this.id = id;
        this.optionValue = optionValue;
        this.categoryCatalogId = categoryCatalogId;
        this.createdAt = createdAt;
        this.parentResponseId = parentResponseId;
        this.questionId = questionId;
        this.updateAt = updateAt;
        this.commentResponse = commentResponse;
        this.optionText = optionText;
    }

    public ResponseOption(int id, int optionValue, int categoryCatalogId, int parentResponseId, int questionId,
            String commentResponse, String optionText) {
        this.id = id;
        this.optionValue = optionValue;
        this.categoryCatalogId = categoryCatalogId;
        this.parentResponseId = parentResponseId;
        this.questionId = questionId;
        this.commentResponse = commentResponse;
        this.optionText = optionText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(int optionValue) {
        this.optionValue = optionValue;
    }

    public int getCategoryCatalogId() {
        return categoryCatalogId;
    }

    public void setCategoryCatalogId(int categoryCatalogId) {
        this.categoryCatalogId = categoryCatalogId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getParentResponseId() {
        return parentResponseId;
    }

    public void setParentResponseId(int parentResponseId) {
        this.parentResponseId = parentResponseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCommentResponse() {
        return commentResponse;
    }

    public void setCommentResponse(String commentResponse) {
        this.commentResponse = commentResponse;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }


    
}

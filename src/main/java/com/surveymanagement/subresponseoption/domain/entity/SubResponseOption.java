package com.surveymanagement.subresponseoption.domain.entity;

public class SubResponseOption {
    private int id;
    private int subResponseNumber;
    private String createdAt;
    private int ResponseOptionId;
    private String updateAt;
    private String subResponseText;
    
    public SubResponseOption(int id, int subResponseNumber, String createdAt, int ResponseOptionId, String updateAt,
            String subResponseText) {
        this.id = id;
        this.subResponseNumber = subResponseNumber;
        this.createdAt = createdAt;
        this.ResponseOptionId = ResponseOptionId;
        this.updateAt = updateAt;
        this.subResponseText = subResponseText;
    }

    public SubResponseOption() {
    }

    public SubResponseOption(int id, int subResponseNumber, int ResponseOptionId, String subResponseText) {
        this.id = id;
        this.subResponseNumber = subResponseNumber;
        this.ResponseOptionId = ResponseOptionId;
        this.subResponseText = subResponseText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubResponseNumber() {
        return subResponseNumber;
    }

    public void setSubResponseNumber(int subResponseNumber) {
        this.subResponseNumber = subResponseNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getResponseOptionId() {
        return ResponseOptionId;
    }

    public void setResponseOptionId(int ResponseOptionId) {
        this.ResponseOptionId = ResponseOptionId;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getSubResponseText() {
        return subResponseText;
    }

    public void setSubResponseText(String subResponseText) {
        this.subResponseText = subResponseText;
    }

    
    

}

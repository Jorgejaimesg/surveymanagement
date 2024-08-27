package com.surveymanagement.responsequestion.domain.entity;

public class ResponseQuestion {
    private int id;
    private int responseId;
    private int subresponseId;
    private String responseText;
    
    public ResponseQuestion() {
    }

    public ResponseQuestion(int id, int responseId, int subresponseId, String responseText) {
        this.id = id;
        this.responseId = responseId;
        this.subresponseId = subresponseId;
        this.responseText = responseText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public int getSubresponseId() {
        return subresponseId;
    }

    public void setSubresponseId(int subresponseId) {
        this.subresponseId = subresponseId;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    
}

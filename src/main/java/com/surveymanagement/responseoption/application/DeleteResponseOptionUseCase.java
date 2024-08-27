package com.surveymanagement.responseoption.application;

import com.surveymanagement.responseoption.domain.service.ResponseOptionService;

public class DeleteResponseOptionUseCase {
    private final ResponseOptionService responseOptionService;

    public DeleteResponseOptionUseCase(ResponseOptionService responseOptionService){
        this.responseOptionService = responseOptionService;
    }

    public void execute(int responseOptionId){
        responseOptionService.deleteResponseOption(responseOptionId);
    }
}

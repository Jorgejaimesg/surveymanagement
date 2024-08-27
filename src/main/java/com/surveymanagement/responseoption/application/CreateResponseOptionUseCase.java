package com.surveymanagement.responseoption.application;

import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;

public class CreateResponseOptionUseCase {
    private final ResponseOptionService responseOptionService;

    public CreateResponseOptionUseCase(ResponseOptionService responseOptionService){
        this.responseOptionService = responseOptionService;
    }

    public void execute(ResponseOption ResponseOption){
        responseOptionService.createResponseOption(ResponseOption);
    }
}

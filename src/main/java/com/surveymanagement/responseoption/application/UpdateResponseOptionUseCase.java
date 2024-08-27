package com.surveymanagement.responseoption.application;

import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;

public class UpdateResponseOptionUseCase {
    private final ResponseOptionService responseOptionService;

    public UpdateResponseOptionUseCase(ResponseOptionService responseOptionService){
        this.responseOptionService = responseOptionService;
    }

    public void execute(ResponseOption ResponseOption){
        responseOptionService.updateResponseOption(ResponseOption);
    }
}

package com.surveymanagement.subresponseoption.application;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

public class CreateSubResponseOptionUseCase {
    private final SubResponseOptionService responseOptionService;

    public CreateSubResponseOptionUseCase(SubResponseOptionService responseOptionService){
        this.responseOptionService = responseOptionService;
    }

    public void execute(SubResponseOption subResponseOption){
        responseOptionService.createSubResponseOption(subResponseOption);
    }
}

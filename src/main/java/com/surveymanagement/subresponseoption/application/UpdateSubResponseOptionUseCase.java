package com.surveymanagement.subresponseoption.application;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

public class UpdateSubResponseOptionUseCase {
    private final SubResponseOptionService responseOptionService;

    public UpdateSubResponseOptionUseCase(SubResponseOptionService responseOptionService){
        this.responseOptionService = responseOptionService;
    }

    public void execute(SubResponseOption subResponseOption){
        responseOptionService.updateSubResponseOption(subResponseOption);
    }
}

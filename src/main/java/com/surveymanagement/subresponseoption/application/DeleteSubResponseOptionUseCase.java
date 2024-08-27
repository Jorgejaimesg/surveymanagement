package com.surveymanagement.subresponseoption.application;

import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

public class DeleteSubResponseOptionUseCase {
    private final SubResponseOptionService subResponseOptionService;

    public DeleteSubResponseOptionUseCase(SubResponseOptionService subResponseOptionService){
        this.subResponseOptionService = subResponseOptionService;
    }

    public void execute(int subResponseOptionId){
        subResponseOptionService.deleteSubResponseOption(subResponseOptionId);
    }
}

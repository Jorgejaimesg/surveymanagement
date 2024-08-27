package com.surveymanagement.responseoption.application;

import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;

import java.util.Optional;

public class FindResponseOptionByIdUseCase {
    private final ResponseOptionService responseOptionService;

    public FindResponseOptionByIdUseCase(ResponseOptionService responseOptionService) {
        this.responseOptionService = responseOptionService;
    }

    public Optional<ResponseOption> execute(int questionId) {
        return responseOptionService.findResponseOptionById(questionId);
    }
}

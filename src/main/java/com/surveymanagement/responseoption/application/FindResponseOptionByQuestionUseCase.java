package com.surveymanagement.responseoption.application;

import com.surveymanagement.responseoption.domain.entity.ResponseOption;
import com.surveymanagement.responseoption.domain.service.ResponseOptionService;

import java.util.List;

public class FindResponseOptionByQuestionUseCase {
    private final ResponseOptionService responseOptionService;

    public FindResponseOptionByQuestionUseCase(ResponseOptionService responseOptionService) {
        this.responseOptionService = responseOptionService;
    }

    public List<ResponseOption> execute(int responseOptionId) {
        return responseOptionService.findResponseOptionByQuestion(responseOptionId);
    }


}

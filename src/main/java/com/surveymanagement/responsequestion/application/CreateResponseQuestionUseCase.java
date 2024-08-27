package com.surveymanagement.responsequestion.application;

import com.surveymanagement.responsequestion.domain.entity.ResponseQuestion;
import com.surveymanagement.responsequestion.domain.service.ResponseQuestionService;

public class CreateResponseQuestionUseCase {
    private final ResponseQuestionService responseQuestionService;

    public CreateResponseQuestionUseCase(ResponseQuestionService responseQuestionService){
        this.responseQuestionService = responseQuestionService;
    }

    public void execute(ResponseQuestion ResponseQuestion){
        responseQuestionService.createResposeQuestion(ResponseQuestion);
    }
}
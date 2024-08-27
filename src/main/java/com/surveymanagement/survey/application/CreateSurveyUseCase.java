package com.surveymanagement.survey.application;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class CreateSurveyUseCase {
    private final SurveyService surveyService;

    public CreateSurveyUseCase(SurveyService surveyService){
        this.surveyService = surveyService;
    }

    public void execute(Survey Survey){
        surveyService.createSurvey(Survey);
    }
}

package com.surveymanagement.survey.application;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class UpdateSurveyUseCase {
    private final SurveyService surveyService;

    public UpdateSurveyUseCase(SurveyService surveyService){
        this.surveyService = surveyService;
    }

    public void execute(Survey survey){
        surveyService.updateSurvey(survey);
    }
}

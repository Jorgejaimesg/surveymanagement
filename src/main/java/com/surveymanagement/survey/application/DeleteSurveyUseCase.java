package com.surveymanagement.survey.application;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class DeleteSurveyUseCase {
    private final SurveyService surveyService;

    public DeleteSurveyUseCase (SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    public Survey execute(int id) {
        return surveyService.deleteSurvey(id);
    }
}

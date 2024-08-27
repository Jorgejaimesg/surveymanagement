package com.surveymanagement.survey.application;

import java.util.List;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class FindAllSurveyUseCase {
    private final SurveyService surveyService;

    public FindAllSurveyUseCase(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    public List<Survey> findAllSurvey() {
        return surveyService.findAllSurvey();
    }
}

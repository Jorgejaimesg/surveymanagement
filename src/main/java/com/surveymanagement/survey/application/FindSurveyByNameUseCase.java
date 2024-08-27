package com.surveymanagement.survey.application;

import java.util.Optional;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class FindSurveyByNameUseCase {
    private final SurveyService surveyService;

    public FindSurveyByNameUseCase(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    public Optional<Survey> findSurveyByName(String surveyName) {
        return surveyService.findSurveyByName(surveyName);
    }
}

package com.surveymanagement.survey.application;

import java.util.Optional;

import com.surveymanagement.survey.domain.entity.Survey;
import com.surveymanagement.survey.domain.service.SurveyService;

public class FindSurveyByCodeUseCase {
    private final SurveyService surveyService;
    public FindSurveyByCodeUseCase(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    public Optional<Survey> findSurveyByCode(int codeSurvey) {
        return surveyService.findSurveyByCode(codeSurvey);
    }
}

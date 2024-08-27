package com.surveymanagement.survey.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.survey.domain.entity.Survey;

public interface SurveyService {
    void createSurvey(Survey survey);
    void updateSurvey(Survey survey);
    Survey deleteSurvey(int id);
    Optional<Survey> findSurveyByName (String name);
    Optional<Survey> findSurveyByCode (int id);
    List<Survey> findAllSurvey();
}

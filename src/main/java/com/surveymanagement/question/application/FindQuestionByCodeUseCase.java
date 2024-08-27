package com.surveymanagement.question.application;

import java.util.Optional;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class FindQuestionByCodeUseCase {
    private final QuestionService questionService;
    public FindQuestionByCodeUseCase(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Optional<Question> findQuestionByCode(int codeQuestion) {
        return questionService.findQuestionByCode(codeQuestion);
    }
}

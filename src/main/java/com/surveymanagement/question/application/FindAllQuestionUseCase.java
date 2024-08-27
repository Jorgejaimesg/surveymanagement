package com.surveymanagement.question.application;

import java.util.List;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class FindAllQuestionUseCase {
    private final QuestionService questionService;

    public FindAllQuestionUseCase(QuestionService questionService) {
        this.questionService = questionService;
    }

    public List<Question> findAllQuestion() {
        return questionService.findAllQuestion();
    }
}

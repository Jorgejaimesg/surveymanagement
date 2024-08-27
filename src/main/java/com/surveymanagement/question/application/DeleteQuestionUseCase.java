package com.surveymanagement.question.application;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class DeleteQuestionUseCase {
    private final QuestionService questionService;

    public DeleteQuestionUseCase (QuestionService questionService) {
        this.questionService = questionService;
    }

    public Question execute(int id) {
        return questionService.deleteQuestion(id);
    }
}

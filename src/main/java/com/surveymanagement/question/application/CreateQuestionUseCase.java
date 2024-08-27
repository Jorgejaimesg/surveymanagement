package com.surveymanagement.question.application;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class CreateQuestionUseCase {
    private final QuestionService questionService;

    public CreateQuestionUseCase(QuestionService questionService){
        this.questionService = questionService;
    }

    public void execute(Question Question){
        questionService.createQuestion(Question);
    }
}

package com.surveymanagement.question.application;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class UpdateQuestionUseCase {
    private final QuestionService questionService;

    public UpdateQuestionUseCase(QuestionService questionService){
        this.questionService = questionService;
    }

    public void execute(Question question){
        questionService.updateQuestion(question);
    }
}

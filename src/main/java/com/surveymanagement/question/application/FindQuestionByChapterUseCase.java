package com.surveymanagement.question.application;

import java.util.List;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class FindQuestionByChapterUseCase {
    private final QuestionService questionService;

    public FindQuestionByChapterUseCase(QuestionService questionService) {
        this.questionService = questionService;
    }

    public List<Question> findQuestionByChapter(int chapter_id) {
        return questionService.findQuestionByChapter(chapter_id);
    }
}

package com.surveymanagement.question.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.question.domain.entity.Question;

public interface QuestionService {
void createQuestion(Question question);
    void updateQuestion(Question question);
    Question deleteQuestion(int id);
    Optional<Question> findQuestionByCode (int id);
    List<Question> findAllQuestion();
    List<Question> findQuestionByChapter(int chapter_id);
}

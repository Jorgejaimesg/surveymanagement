package com.surveymanagement.chapter.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.chapter.domain.entity.Chapter;

public interface ChapterService {
    void createChapter(Chapter chapter);
    void updateChapter(Chapter chapter);
    Chapter deleteChapter(int id);
    Optional<Chapter> findChapterByName (String name, int survey_id);
    Optional<Chapter> findChapterByCode (int id);
    List<Chapter> findAllChapter();
    List<Chapter> findChapterBySurvey(int survey_id);
}

package com.surveymanagement.chapter.application;

import java.util.List;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class FindChapterBySurveyUseCase {
    private final ChapterService chapterService;

    public FindChapterBySurveyUseCase(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public List<Chapter> findChapterBySurvey(int survey_id) {
        return chapterService.findChapterBySurvey(survey_id);
    }
}

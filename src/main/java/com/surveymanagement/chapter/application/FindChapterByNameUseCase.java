package com.surveymanagement.chapter.application;

import java.util.Optional;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class FindChapterByNameUseCase {
    private final ChapterService chapterService;

    public FindChapterByNameUseCase(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public Optional<Chapter> findChapterByName(String name, int survey_id) {
        return chapterService.findChapterByName(name, survey_id);
    }
}

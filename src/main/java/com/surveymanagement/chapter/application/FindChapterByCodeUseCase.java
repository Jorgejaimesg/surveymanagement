package com.surveymanagement.chapter.application;

import java.util.Optional;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class FindChapterByCodeUseCase {
    private final ChapterService chapterService;
    public FindChapterByCodeUseCase(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public Optional<Chapter> findChapterByCode(int codeChapter) {
        return chapterService.findChapterByCode(codeChapter);
    }
}

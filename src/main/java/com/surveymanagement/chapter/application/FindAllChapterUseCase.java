package com.surveymanagement.chapter.application;

import java.util.List;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class FindAllChapterUseCase {
    private final ChapterService chapterService;

    public FindAllChapterUseCase(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public List<Chapter> findAllChapter() {
        return chapterService.findAllChapter();
    }
}

package com.surveymanagement.chapter.application;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class DeleteChapterUseCase {
    private final ChapterService chapterService;

    public DeleteChapterUseCase (ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public Chapter execute(int id) {
        return chapterService.deleteChapter(id);
    }
}

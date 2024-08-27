package com.surveymanagement.chapter.application;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class UpdateChapterUseCase {
    private final ChapterService chapterService;

    public UpdateChapterUseCase(ChapterService chapterService){
        this.chapterService = chapterService;
    }

    public void execute(Chapter chapter){
        chapterService.updateChapter(chapter);
    }
}

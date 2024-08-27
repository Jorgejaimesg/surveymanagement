package com.surveymanagement.chapter.application;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class CreateChapterUseCase {
    private final ChapterService chapterService;

    public CreateChapterUseCase(ChapterService chapterService){
        this.chapterService = chapterService;
    }

    public void execute(Chapter Chapter){
        chapterService.createChapter(Chapter);
    }
}

package com.surveymanagement.responsequestion.domain.service;

import com.surveymanagement.responsequestion.domain.entity.ResponseQuestion;

import java.util.List;

public interface ResponseQuestionService {
    void createResposeQuestion (ResponseQuestion responseQuestion);
    ResponseQuestion deleteResponseQuestion (int resposeQuestioId);
    List<ResponseQuestion> findAllResponseQuestion();

}

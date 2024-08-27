package com.surveymanagement.responseoption.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.responseoption.domain.entity.ResponseOption;

public interface ResponseOptionService {
    void createResponseOption (ResponseOption responseOption);
    void updateResponseOption (ResponseOption responseOption);
    ResponseOption deleteResponseOption (int responseOptionId);
    Optional<ResponseOption> findResponseOptionById(int responseOptionId);
    List<ResponseOption> findResponseOptionByQuestion(int questionId);
}

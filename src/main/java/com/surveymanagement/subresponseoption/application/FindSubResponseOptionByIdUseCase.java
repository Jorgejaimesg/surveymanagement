package com.surveymanagement.subresponseoption.application;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

import java.util.Optional;

public class FindSubResponseOptionByIdUseCase {
    private final SubResponseOptionService subResponseOptionService;

    public FindSubResponseOptionByIdUseCase(SubResponseOptionService subResponseOptionService) {
        this.subResponseOptionService = subResponseOptionService;
    }

    public Optional<SubResponseOption> execute(int subresponseId) {
        return subResponseOptionService.findSubResponseOptionById(subresponseId);
    }
}

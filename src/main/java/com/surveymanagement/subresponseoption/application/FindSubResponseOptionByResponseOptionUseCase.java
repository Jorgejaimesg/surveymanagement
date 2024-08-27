package com.surveymanagement.subresponseoption.application;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;
import com.surveymanagement.subresponseoption.domain.service.SubResponseOptionService;

import java.util.List;

public class FindSubResponseOptionByResponseOptionUseCase {
    private final SubResponseOptionService subResponseOptionService;

    public FindSubResponseOptionByResponseOptionUseCase(SubResponseOptionService subResponseOptionService) {
        this.subResponseOptionService = subResponseOptionService;
    }

    public List<SubResponseOption> execute(int responseOptionId) {
        return subResponseOptionService.findSubResponseOptionByResponseOption(responseOptionId);
    }
}

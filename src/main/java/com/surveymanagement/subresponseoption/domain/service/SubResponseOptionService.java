package com.surveymanagement.subresponseoption.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.subresponseoption.domain.entity.SubResponseOption;

public interface SubResponseOptionService {
    void createSubResponseOption (SubResponseOption subResponseOption);
    void updateSubResponseOption (SubResponseOption subResponseOption);
    SubResponseOption deleteSubResponseOption (int subResponseOptionId);
    Optional<SubResponseOption> findSubResponseOptionById(int subResponseOptionId);
    List<SubResponseOption> findSubResponseOptionByResponseOption(int responseOptionId);
}

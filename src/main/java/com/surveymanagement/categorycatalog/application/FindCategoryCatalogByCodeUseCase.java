package com.surveymanagement.categorycatalog.application;

import java.util.Optional;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class FindCategoryCatalogByCodeUseCase {
    private final CategoryCatalogService categorycatalogService;
    public FindCategoryCatalogByCodeUseCase(CategoryCatalogService categorycatalogService) {
        this.categorycatalogService = categorycatalogService;
    }

    public Optional<CategoryCatalog> findCategoryCatalogByCode(int codeCategoryCatalog) {
        return categorycatalogService.findCategoryCatalogByCode(codeCategoryCatalog);
    }
}

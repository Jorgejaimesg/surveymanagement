package com.surveymanagement.categorycatalog.application;

import java.util.Optional;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class FindCategoryCatalogByNameUseCase {
    private final CategoryCatalogService categorycatalogService;

    public FindCategoryCatalogByNameUseCase(CategoryCatalogService categorycatalogService) {
        this.categorycatalogService = categorycatalogService;
    }

    public Optional<CategoryCatalog> findCategoryCatalogByName(String categorycatalogName) {
        return categorycatalogService.findCategoryCatalogByName(categorycatalogName);
    }
}

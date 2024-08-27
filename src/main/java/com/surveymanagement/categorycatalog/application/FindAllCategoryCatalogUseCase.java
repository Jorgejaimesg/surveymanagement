package com.surveymanagement.categorycatalog.application;

import java.util.List;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class FindAllCategoryCatalogUseCase {
    private final CategoryCatalogService categorycatalogService;

    public FindAllCategoryCatalogUseCase(CategoryCatalogService categorycatalogService) {
        this.categorycatalogService = categorycatalogService;
    }

    public List<CategoryCatalog> findAllCategoryCatalog() {
        return categorycatalogService.findAllCategoryCatalog();
    }
}

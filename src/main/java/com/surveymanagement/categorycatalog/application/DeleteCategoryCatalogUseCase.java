package com.surveymanagement.categorycatalog.application;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class DeleteCategoryCatalogUseCase {
    private final CategoryCatalogService categorycatalogService;

    public DeleteCategoryCatalogUseCase (CategoryCatalogService categorycatalogService) {
        this.categorycatalogService = categorycatalogService;
    }

    public CategoryCatalog execute(int id) {
        return categorycatalogService.deleteCategoryCatalog(id);
    }
}

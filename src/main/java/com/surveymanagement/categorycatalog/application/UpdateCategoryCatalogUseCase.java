package com.surveymanagement.categorycatalog.application;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class UpdateCategoryCatalogUseCase {
    private final CategoryCatalogService categorycatalogService;

    public UpdateCategoryCatalogUseCase(CategoryCatalogService categorycatalogService){
        this.categorycatalogService = categorycatalogService;
    }

    public void execute(CategoryCatalog categorycatalog){
        categorycatalogService.updateCategoryCatalog(categorycatalog);
    }
}

package com.surveymanagement.categorycatalog.application;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;
import com.surveymanagement.categorycatalog.domain.service.CategoryCatalogService;

public class CreateCategoryCatalogUseCase {
    private final CategoryCatalogService categoryCatalogService;

    public CreateCategoryCatalogUseCase(CategoryCatalogService categoryCatalogService){
        this.categoryCatalogService = categoryCatalogService;
    }

    public void execute(CategoryCatalog CategoryCatalog){
        categoryCatalogService.createCategoryCatalog(CategoryCatalog);
    }
}

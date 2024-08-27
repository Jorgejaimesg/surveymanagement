package com.surveymanagement.categorycatalog.domain.service;

import java.util.List;
import java.util.Optional;

import com.surveymanagement.categorycatalog.domain.entity.CategoryCatalog;

public interface CategoryCatalogService {
    void createCategoryCatalog(CategoryCatalog categoryCatalog);
    void updateCategoryCatalog(CategoryCatalog categoryCatalog);
    CategoryCatalog deleteCategoryCatalog(int id);
    Optional<CategoryCatalog> findCategoryCatalogByName (String name);
    Optional<CategoryCatalog> findCategoryCatalogByCode (int id);
    List<CategoryCatalog> findAllCategoryCatalog();
}

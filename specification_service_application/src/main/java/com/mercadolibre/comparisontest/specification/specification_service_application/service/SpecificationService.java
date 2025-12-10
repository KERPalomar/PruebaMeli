package com.mercadolibre.comparisontest.specification.specification_service_application.service;

import java.util.List;
import java.util.Map;

import com.mercadolibre.comparisontest.specification.specification_service_application.model.dto.SpecificationCategoryDTO;
import com.mercadolibre.comparisontest.specification.specification_service_application.model.dto.SpecificationDTO;

public interface SpecificationService {

    List<SpecificationDTO> getSpecificationByProductId(Long productId);
    Map<Long, List<SpecificationDTO>> getSpecificationForProducts(List<Long> productIds);
    List<SpecificationDTO> getSpecificationByProductAndCategory(Long productId, String categoryId);
    Map<Long, List<SpecificationDTO>> getSpecificationForProductsAndCategory(List<Long> productIds, String categoryId);
    List<SpecificationCategoryDTO> getAllCategories();
    SpecificationCategoryDTO getSpecificationcategoryById(String specificationCategoryId);
}

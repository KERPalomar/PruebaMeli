package com.mercadolibre.comparisontest.specification.specification_service_application.service.impl;

import com.mercadolibre.comparisontest.specification.specification_service_application.exception.CategoryNotFoundException;
import com.mercadolibre.comparisontest.specification.specification_service_application.exception.SpecificationNotFoundException;
import com.mercadolibre.comparisontest.specification.specification_service_application.model.Specification;
import com.mercadolibre.comparisontest.specification.specification_service_application.model.SpecificationCategory;
import com.mercadolibre.comparisontest.specification.specification_service_application.model.dto.*;
import com.mercadolibre.comparisontest.specification.specification_service_application.repository.SpecificationCategoryRepository;
import com.mercadolibre.comparisontest.specification.specification_service_application.repository.SpecificationRepository;
import com.mercadolibre.comparisontest.specification.specification_service_application.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    private final SpecificationRepository specificationRepository;
    private final SpecificationCategoryRepository categoryRepository;

    @Autowired
    public SpecificationServiceImpl(SpecificationRepository specificationRepository, SpecificationCategoryRepository categoryRepository) {
        this.specificationRepository = specificationRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SpecificationDTO> getSpecificationByProductId(Long productId) {
        List<Specification> specifications = specificationRepository.findByProductId(productId);
        if (specifications.isEmpty()) {
            throw new SpecificationNotFoundException("No specifications found for product ID: " + productId);
        }
        return specifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<SpecificationDTO>> getSpecificationForProducts(List<Long> productIds) {
        List<Specification> specifications = specificationRepository.findByProductIdIn(productIds);
        
        return specifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.groupingBy(SpecificationDTO::getProductId));
    }

    @Override
    public List<SpecificationDTO> getSpecificationByProductAndCategory(Long productId, String categoryId) {
        // Verificar que la categoría existe
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        
        List<Specification> specifications = specificationRepository.findByProductIdAndCategoryId(productId, categoryId);
        if (specifications.isEmpty()) {
            throw new SpecificationNotFoundException(
                    "No specifications found for product ID: " + productId + " and category ID: " + categoryId);
        }
        
        return specifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<SpecificationDTO>> getSpecificationForProductsAndCategory(List<Long> productIds, String categoryId) {
        // Verificar que la categoría existe
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
        
        List<Specification> specifications = specificationRepository.findByProductIdInAndCategoryId(productIds, categoryId);
        
        return specifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.groupingBy(SpecificationDTO::getProductId));
    }

    @Override
    public List<SpecificationCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SpecificationCategoryDTO getSpecificationcategoryById(String specificationcategoryId) {
        SpecificationCategory category = categoryRepository.findById(specificationcategoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + specificationcategoryId));
        
        return convertToCategoryDTO(category);
    }

    // Método auxiliar para convertir entidad a DTO
    private SpecificationDTO convertToDTO(Specification specification) {
        return new SpecificationDTO(
                specification.getId(),
                specification.getProductId(),
                specification.getCategory().getId(),
                specification.getCategory().getName(),
                specification.getName(),
                specification.getPrice(),
                specification.getUnit(),
                specification.getDescription(),
                specification.isComparable()
        );
    }

    // Método auxiliar para convertir entidad a DTO
    private SpecificationCategoryDTO convertToCategoryDTO(SpecificationCategory category) {
        return new SpecificationCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}

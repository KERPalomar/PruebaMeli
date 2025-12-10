package com.mercadolibre.comparisontest.specification.specification_service_application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.comparisontest.specification.specification_service_application.model.dto.*;
import com.mercadolibre.comparisontest.specification.specification_service_application.service.SpecificationService;

@RestController
@RequestMapping("/specifications")
public class SpecificationController {

    private final SpecificationService specificationService;
    
    @Autowired
    public SpecificationController(SpecificationService specificationService){
        this.specificationService = specificationService;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<List<SpecificationDTO>> getSpecificationsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(specificationService.getSpecificationByProductId(productId));
    }

    @PostMapping("/bulk")
    public ResponseEntity<SpecificationPackResponseDTO> getSpecificationsInPac(@RequestBody SpecificationPackRequestDTO request) {
        Map<Long, List<SpecificationDTO>> specs;
        
        if (request.getCategoryId() != null && !request.getCategoryId().isEmpty()) {
            specs = specificationService.getSpecificationForProductsAndCategory(
                    request.getProductIds(), request.getCategoryId());
        } else {
            specs = specificationService.getSpecificationForProducts(request.getProductIds());
        }
        
        return ResponseEntity.ok(new SpecificationPackResponseDTO(specs));
    }

    @GetMapping("/products/{productId}/categories/{categoryId}")
    public ResponseEntity<List<SpecificationDTO>> getSpecificationsByProductAndCategory( @PathVariable Long productId, @PathVariable String categoryId) {
        return ResponseEntity.ok(
                specificationService.getSpecificationByProductAndCategory(productId, categoryId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<SpecificationCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(specificationService.getAllCategories());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<SpecificationCategoryDTO> getCategoryById(@PathVariable String categoryId) {
        return ResponseEntity.ok(specificationService.getSpecificationcategoryById(categoryId));
    }

}

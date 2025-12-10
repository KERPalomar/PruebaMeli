package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonResponse;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.service.ComparisonService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comparisons")
public class ComparisonController {
    
    private final ComparisonService comparisonService;

    @Autowired
    public ComparisonController(ComparisonService comparisonService){
        this.comparisonService = comparisonService; 
    }

    @PostMapping
    public ResponseEntity<ComparisonResponse> compareProducts(@Valid @RequestBody ComparisonRequest request){
        ComparisonResponse response = comparisonService.compareProducts(request);
        return ResponseEntity.ok(response);
    }

}

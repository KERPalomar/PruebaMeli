package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationBulkRequest {
    
    private List<Long> productIds;
    private String categoryId;

}

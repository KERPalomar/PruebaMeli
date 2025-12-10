package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationDTO {
    private Long id;
    private Long productId;
    private String categoryId;
    private String categoryName;
    private String name;
    private String price;
    private String unit;
    private String description;
    private boolean comparable;
}

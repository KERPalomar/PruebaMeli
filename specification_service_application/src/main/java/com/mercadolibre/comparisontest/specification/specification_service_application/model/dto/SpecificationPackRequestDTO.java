package com.mercadolibre.comparisontest.specification.specification_service_application.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationPackRequestDTO {
    private List<Long> productIds;
    private String categoryId;
}

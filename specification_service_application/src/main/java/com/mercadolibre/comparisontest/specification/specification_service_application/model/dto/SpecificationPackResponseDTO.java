package com.mercadolibre.comparisontest.specification.specification_service_application.model.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationPackResponseDTO {
    private Map<Long, List<SpecificationDTO>> specifications;
}

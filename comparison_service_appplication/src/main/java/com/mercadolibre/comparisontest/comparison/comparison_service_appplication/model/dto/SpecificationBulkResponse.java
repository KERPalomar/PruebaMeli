package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationBulkResponse {
    private Map<Long, List<SpecificationDTO>> specifications;
}

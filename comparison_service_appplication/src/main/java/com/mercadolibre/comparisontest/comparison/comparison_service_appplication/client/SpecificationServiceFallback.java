package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkResponse;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationDTO;

public class SpecificationServiceFallback implements SpecificationServiceClient {

    @Override
    public List<SpecificationDTO> getSpecificationsForProduct(Long productId) {
        return Collections.emptyList();
    }

    @Override
    public SpecificationBulkResponse getSpecificationsBulk(SpecificationBulkRequest request) {
        return new SpecificationBulkResponse(new HashMap<>());
    }
    
}

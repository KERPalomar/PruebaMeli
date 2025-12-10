package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.service;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonResponse;

public interface ComparisonService {

    ComparisonResponse compareProducts(ComparisonRequest request);
    
}

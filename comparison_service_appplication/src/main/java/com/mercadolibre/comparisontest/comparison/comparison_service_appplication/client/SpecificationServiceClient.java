package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkResponse;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationDTO;

@FeignClient(name = "specification-service", url = "${service.specification-service.url}", path = "/api/specifications")
public interface SpecificationServiceClient {

    @GetMapping("/products/{productId}")
    List<SpecificationDTO> getSpecificationsForProduct(@PathVariable("productId") Long productId);

    @PostMapping("/bulk")
    SpecificationBulkResponse getSpecificationsBulk(@RequestBody SpecificationBulkRequest request);
    
}

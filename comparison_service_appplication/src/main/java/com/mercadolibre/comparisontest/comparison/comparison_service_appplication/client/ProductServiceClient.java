package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ProductDTO;

@FeignClient(name = "product-service", url = "${service.product-service.url}", path = "/api/products")
public interface ProductServiceClient {
    
    @GetMapping("/{productId}")
    ProductDTO getProduct(@PathVariable("productId") Long productId);

    @PostMapping("/bulk")
    List<ProductDTO> getProductsBulk(@RequestBody List<Long> productsId);

}

package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ProductDTO;

@Component
public class ProductServiceFallback implements ProductServiceClient {

    @Override
    public ProductDTO getProduct(Long productId) {

        ProductDTO fallBackProduct = new ProductDTO();
        fallBackProduct.setId(productId);
        fallBackProduct.setName("Producto no disponible");
        fallBackProduct.setDescription("Información del producto temporalmente no disponible");
        fallBackProduct.setPrice(BigDecimal.ZERO);
        fallBackProduct.setAvailable(false);

        return fallBackProduct;
    }

    @Override
    public List<ProductDTO> getProductsBulk(List<Long> productsId) {
        if(productsId == null || productsId.isEmpty()){
            return Collections.emptyList();
        }

        return productsId.stream()
            .map(this::getProduct)
            .collect(Collectors.toList());
    }
    
}

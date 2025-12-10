package com.mercadolibre.comparisontest.product.product_service_application.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String menssaje){
        super(menssaje);
    }
}

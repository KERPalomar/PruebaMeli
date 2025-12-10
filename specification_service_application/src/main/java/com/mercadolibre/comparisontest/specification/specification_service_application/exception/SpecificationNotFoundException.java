package com.mercadolibre.comparisontest.specification.specification_service_application.exception;

public class SpecificationNotFoundException extends RuntimeException {
    public SpecificationNotFoundException(String message){
        super(message);
    }
}

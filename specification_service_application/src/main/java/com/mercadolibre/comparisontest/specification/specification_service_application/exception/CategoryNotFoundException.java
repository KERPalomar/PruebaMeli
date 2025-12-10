package com.mercadolibre.comparisontest.specification.specification_service_application.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }
}

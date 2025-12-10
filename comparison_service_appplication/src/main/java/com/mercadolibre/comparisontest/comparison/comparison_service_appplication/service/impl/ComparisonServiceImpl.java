package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client.ProductServiceClient;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client.SpecificationServiceClient;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.exception.ComparisonException;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ComparisonResponse;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.ProductDTO;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkRequest;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationBulkResponse;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto.SpecificationDTO;
import com.mercadolibre.comparisontest.comparison.comparison_service_appplication.service.ComparisonService;

@Service
public class ComparisonServiceImpl implements ComparisonService{

    private final ProductServiceClient productServiceClient;
    private final SpecificationServiceClient specificationServiceClient;

    @Autowired
    public ComparisonServiceImpl(ProductServiceClient productServiceClient, SpecificationServiceClient specificationServiceClient){
        this.productServiceClient = productServiceClient;
        this.specificationServiceClient = specificationServiceClient;
    }

    @Override
    public ComparisonResponse compareProducts(ComparisonRequest request) {
        
        if(request.getProductIds() == null || request.getProductIds().size() < 2){
            throw new ComparisonException("Se requieren al menos 2 productos para comparar");
        }

        // 1. Obtener información de productos
        List<ProductDTO> products = productServiceClient.getProductsBulk(request.getProductIds());
        if (products.isEmpty()) {
            throw new ComparisonException("No se pudieron obtener los productos solicitados");
        }
        
        // 2. Obtener especificaciones de productos
        SpecificationBulkRequest specRequest = new SpecificationBulkRequest();
        specRequest.setProductIds(request.getProductIds());
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            specRequest.setCategoryId(request.getCategoryIds().get(0)); // Por simplicidad, tomamos solo la primera categoría
        }
        
        SpecificationBulkResponse specResponse = specificationServiceClient.getSpecificationsBulk(specRequest);
        Map<Long, List<SpecificationDTO>> specificationsByProduct = 
            specResponse != null ? specResponse.getSpecifications() : new HashMap<>();
        
        // 3. Organizar especificaciones por categoría
        List<ComparisonResponse.SpecificationCategoryGroup> categoryGroups = 
            organizeSpecificationsByCategory(specificationsByProduct, request.getProductIds());
        
        // 4. Construir respuesta
        return ComparisonResponse.builder()
            .products(products)
            .specificationCategories(categoryGroups)
            .build();

    }

    private List<ComparisonResponse.SpecificationCategoryGroup> organizeSpecificationsByCategory(
            Map<Long, List<SpecificationDTO>> specificationsByProduct, List<Long> productIds) {
        
        // Si no hay especificaciones, devolver lista vacía
        if (specificationsByProduct == null || specificationsByProduct.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 1. Agrupar todas las especificaciones por categoría
        Map<String, Map<String, Map<Long, SpecificationDTO>>> specsByCategory = new HashMap<>();
        
        // Para cada producto y sus especificaciones
        specificationsByProduct.forEach((productId, specifications) -> {
            // Para cada especificación
            specifications.forEach(spec -> {
                // Obtener o crear el mapa
				// Obtener o crear el mapa de categorías
                Map<String, Map<Long, SpecificationDTO>> categorySpecs = 
                    specsByCategory.computeIfAbsent(spec.getCategoryId() + ":" + spec.getCategoryName(), 
                                                   k -> new HashMap<>());
                
                // Obtener o crear el mapa de especificaciones por nombre
                Map<Long, SpecificationDTO> specsByName = 
                    categorySpecs.computeIfAbsent(spec.getName(), k -> new HashMap<>());
                
                // Agregar la especificación al mapa
                specsByName.put(productId, spec);
            });
        });
        
        // 2. Convertir el mapa a la estructura de respuesta
        List<ComparisonResponse.SpecificationCategoryGroup> result = new ArrayList<>();
        
        specsByCategory.forEach((categoryKey, specsByName) -> {
            String[] categoryParts = categoryKey.split(":", 2);
            String categoryId = categoryParts[0];
            String categoryName = categoryParts.length > 1 ? categoryParts[1] : categoryId;
            
            List<ComparisonResponse.SpecificationComparisonItem> comparisonItems = new ArrayList<>();
            
            // Para cada nombre de especificación
            specsByName.forEach((specName, productSpecs) -> {
                // Crear mapa de valores por producto
                Map<Long, ComparisonResponse.SpecificationPrice> valuesByProduct = new HashMap<>();
                
                // Determinar si es comparable para destacar mejor valor
                boolean isComparable = productSpecs.values().stream()
                    .anyMatch(SpecificationDTO::isComparable);
                
                // Procesamiento para destacar el mejor valor (si es comparable)
                if (isComparable) {
                    // Intentar comparar valores numéricos
                    try {
                        // Recopilar valores numéricos
                        Map<Long, Double> numericValues = new HashMap<>();
                        productSpecs.forEach((pid, spec) -> {
                            try {
                                numericValues.put(pid, Double.parseDouble(spec.getPrice()));
                            } catch (NumberFormatException e) {
                                // No es un valor numérico, ignorar para la comparación
                            }
                        });
                        
                        // Si hay valores numéricos, encontrar el mejor
                        if (!numericValues.isEmpty()) {
                            // Determinar si un valor más alto es mejor (por defecto) o más bajo es mejor
                            boolean higherIsBetter = true; // Podría determinarse por la categoría o nombre de la especificación
                            
                            // Encontrar el mejor valor
                            Map.Entry<Long, Double> bestEntry = higherIsBetter ? 
                                Collections.max(numericValues.entrySet(), Map.Entry.comparingByValue()) :
                                Collections.min(numericValues.entrySet(), Map.Entry.comparingByValue());
                            
                            // Crear los valores con destacado para el mejor
                            productSpecs.forEach((pid, spec) -> {
                                boolean isHighlighted = numericValues.containsKey(pid) && 
                                                       pid.equals(bestEntry.getKey());
                                
                                valuesByProduct.put(pid, ComparisonResponse.SpecificationPrice.builder()
                                    .price(spec.getPrice())
                                    .unit(spec.getUnit())
                                    .highlight(isHighlighted)
                                    .build());
                            });
                        } else {
                            // Si no hay valores numéricos, crear valores sin destacar
                            createStandardValues(productSpecs, valuesByProduct);
                        }
                    } catch (Exception e) {
                        // En caso de cualquier error, crear valores sin destacar
                        createStandardValues(productSpecs, valuesByProduct);
                    }
                } else {
                    // Si no es comparable, crear valores sin destacar
                    createStandardValues(productSpecs, valuesByProduct);
                }
                
                // Asegurarse de que todos los productos solicitados tienen un valor (aunque sea vacío)
                productIds.forEach(pid -> {
                    if (!valuesByProduct.containsKey(pid)) {
                        valuesByProduct.put(pid, ComparisonResponse.SpecificationPrice.builder()
                            .price("-")
                            .unit("")
                            .highlight(false)
                            .build());
                    }
                });
                
                // Agregar el item de comparación
                comparisonItems.add(ComparisonResponse.SpecificationComparisonItem.builder()
                    .name(specName)
                    .prices(valuesByProduct)
                    .build());
            });
            
            // Agregar el grupo de categoría
            result.add(ComparisonResponse.SpecificationCategoryGroup.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .specifications(comparisonItems)
                .build());
        });
        
        return result;            

    }

    /** * Crea valores estándar sin destacar para especificaciones */
    private void createStandardValues(Map<Long, SpecificationDTO> productSpecs, Map<Long, ComparisonResponse.SpecificationPrice> valuesByProduct) {
        productSpecs.forEach((pid, spec) -> {
            valuesByProduct.put(pid, ComparisonResponse.SpecificationPrice.builder()
                .price(spec.getPrice())
                .unit(spec.getUnit())
                .highlight(false)
                .build());
        });
    }
    
}

package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonResponse {
    private List<ProductDTO> products;
    private List<SpecificationCategoryGroup> specificationCategories;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecificationCategoryGroup {
        private String categoryId;
        private String categoryName;
        private List<SpecificationComparisonItem> specifications;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecificationComparisonItem {
        private String name;
        private Map<Long, SpecificationPrice> prices; // productId -> valor
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecificationPrice {
        private String price;
        private String unit;
        private boolean highlight; // Para destacar mejor valor en comparaciones
    }
}

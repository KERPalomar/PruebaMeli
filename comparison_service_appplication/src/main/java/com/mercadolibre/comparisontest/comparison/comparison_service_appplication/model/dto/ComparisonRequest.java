package com.mercadolibre.comparisontest.comparison.comparison_service_appplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonRequest {
    @NotEmpty(message = "Al menos un ID de producto debe ser proporcionado")
    @Size(min = 2, max = 5, message = "Se deben proporcionar entre 2 y 5 productos para comparar")
    private List<Long> productIds;
    
    private List<String> categoryIds; // Opcional, para filtrar por categorías específicas
}

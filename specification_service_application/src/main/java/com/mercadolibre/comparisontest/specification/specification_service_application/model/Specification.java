package com.mercadolibre.comparisontest.specification.specification_service_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long productId;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private SpecificationCategory category;
    
    private String name;
    private String price;
    private String unit;
    private String description;
    private boolean comparable;
}

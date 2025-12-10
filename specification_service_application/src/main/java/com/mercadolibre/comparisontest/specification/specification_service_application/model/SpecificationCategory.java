package com.mercadolibre.comparisontest.specification.specification_service_application.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specification_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationCategory {
    @Id
    private String id;
    private String name;
    private String description;
}

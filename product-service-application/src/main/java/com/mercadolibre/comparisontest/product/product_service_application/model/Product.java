package com.mercadolibre.comparisontest.product.product_service_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(nullable = false)
    private Double rating;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "brand_id")
    private Long brandId;
    
    private boolean available;
}
package com.mercadolibre.comparisontest.specification.specification_service_application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.comparisontest.specification.specification_service_application.model.Specification;

import lombok.experimental.Accessors;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {
    List<Specification> findByProductId(Long productId);
    List<Specification> findByProductIdIn(List<Long> productId);
    List<Specification> findByProductIdAndCategoryId(Long productId, String categoryId);
    List<Specification> findByProductIdInAndCategoryId(List<Long> productIds, String categoryId);
}

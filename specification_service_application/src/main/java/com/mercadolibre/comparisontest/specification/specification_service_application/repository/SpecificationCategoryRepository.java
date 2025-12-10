package com.mercadolibre.comparisontest.specification.specification_service_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.comparisontest.specification.specification_service_application.model.SpecificationCategory;

@Repository
public interface SpecificationCategoryRepository extends JpaRepository<SpecificationCategory, String>{

}

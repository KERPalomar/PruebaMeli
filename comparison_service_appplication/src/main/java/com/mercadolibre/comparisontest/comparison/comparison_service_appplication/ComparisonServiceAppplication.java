package com.mercadolibre.comparisontest.comparison.comparison_service_appplication;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mercadolibre.comparisontest.comparison.comparison_service_appplication.client")
@ComponentScan(basePackages = "com.mercadolibre.comparisontest.comparison")
public class ComparisonServiceAppplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparisonServiceAppplication.class, args);
	}

}

# ⚡ Quick Reference — PruebaMeli

## 🚀 START HERE

```bash
./scripts/start-all.sh    # Inicia todo
./scripts/stop-all.sh     # Detiene todo
```

## 🌐 URLs Rápidas

| Recurso | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| Product API | http://localhost:8081/api |
| Specification API | http://localhost:8083/api |
| Comparison API | http://localhost:8082/api |
| Product Swagger | http://localhost:8081/api/swagger-ui.html |
| Spec Swagger | http://localhost:8083/api/swagger-ui.html |
| Compare Swagger | http://localhost:8082/api/swagger-ui.html |

## ��️ Bases de Datos

```
Usuario: sa
Contraseña: password

Product DB:       http://localhost:8081/api/h2-console
Specification DB: http://localhost:8083/api/h2-console
```

## 📝 Curl Ejemplos Rápidos

### Product Service
```bash
# Listar todos
curl http://localhost:8081/api/products

# Obtener uno
curl http://localhost:8081/api/products/1

# Crear
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","description":"XPS","price":1299.99,"available":true}'

# Actualizar
curl -X PUT http://localhost:8081/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop Updated","description":"XPS13","price":1399.99,"available":true}'

# Eliminar
curl -X DELETE http://localhost:8081/api/products/1

# Comparar
curl "http://localhost:8081/api/products/compare?ids=1&ids=2&ids=3"
```

### Specification Service
```bash
# Specs de un producto
curl http://localhost:8083/api/specifications/products/1

# Todas las categorías
curl http://localhost:8083/api/specifications/categories

# Categoría específica
curl http://localhost:8083/api/specifications/categories/electronics

# Specs de producto + categoría
curl http://localhost:8083/api/specifications/products/1/categories/electronics

# Bulk query
curl -X POST http://localhost:8083/api/specifications/bulk \
  -H "Content-Type: application/json" \
  -d '{"productIds":[1,2,3],"categoryId":["electronics"]}'
```

### Comparison Service
```bash
# Comparar productos
curl -X POST http://localhost:8082/api/comparisons \
  -H "Content-Type: application/json" \
  -d '{"productIds":[1,2,3],"categoryId":["electronics"]}'
```

## 🏥 Health Checks

```bash
curl http://localhost:8081/api/actuator/health   # Product
curl http://localhost:8083/api/actuator/health   # Specification
curl http://localhost:8082/api/actuator/health   # Comparison
```

## 📊 Logs

```bash
tail -f eureka-server/eureka.log
tail -f product-service-application/product.log
tail -f specification_service_application/specification.log
tail -f comparison_service_appplication/comparison.log
```

## 🔌 Puertos

- **8761** — Eureka Server
- **8081** — Product Service
- **8082** — Comparison Service
- **8083** — Specification Service

## 📚 Documentación

| Archivo | Contenido |
|---------|-----------|
| README.md | Guía general y descripción |
| ARCHITECTURE.md | Diagramas, flujos, BDs |
| DOCUMENTATION.md | Índice y estructura completa |
| product-service-application/API_EXAMPLES.md | Ejemplos Product API |
| specification_service_application/API_EXAMPLES.md | Ejemplos Specification API |
| comparison_service_appplication/API_EXAMPLES.md | Ejemplos Comparison API |

## 🆘 Troubleshooting Rápido

| Problema | Solución |
|----------|----------|
| Puerto en uso | `lsof -i :PORT \| xargs kill -9` |
| Eureka no sube | Verifica puerto 8761 libre, revisa logs |
| Servicios no se registran | Espera 30s, revisa `/actuator/health` |
| BD vacía | H2 In-Memory = datos perdidos al restart |
| Comparación falla | Verifica que Product y Spec estén UP |

## 📌 Stack

- **Spring Boot** 4.0.0 (Eureka) / 3.3.5 (otros)
- **Eureka** Service Discovery
- **H2** In-Memory Database
- **Feign** Llamadas HTTP entre servicios
- **Resilience4j** Circuit Breaker
- **Swagger** API Documentation
- **Java** 21
- **Maven** 3.8+

## 🎯 Casos de Uso

1. **Gestionar Productos** → Product Service (CRUD)
2. **Consultar Especificaciones** → Specification Service
3. **Comparar Productos** → Comparison Service (orquestador)
4. **Ver Servicios Registrados** → Eureka Dashboard

## ✅ Checklist Rápido

- [ ] Java 21+ instalado
- [ ] Ejecutar `./scripts/start-all.sh`
- [ ] Eureka arriba: http://localhost:8761
- [ ] Product Service up: http://localhost:8081/api/actuator/health
- [ ] Spec Service up: http://localhost:8083/api/actuator/health
- [ ] Comparison Service up: http://localhost:8082/api/actuator/health

---

**Última actualización**: 09/12/2025

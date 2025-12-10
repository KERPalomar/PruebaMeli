# Comparison Service — API Examples

**Puerto**: 8082  
**Context Path**: `/api`  
**Base URL**: `http://localhost:8082/api`  
**BD**: Sin BD propia (orquestador que llama a otros servicios)  
**Swagger**: http://localhost:8082/api/swagger-ui.html

## �� Endpoints

### 1. Comparar productos (POST /comparisons)

Este endpoint orquesta llamadas a **Product Service** y **Specification Service** para obtener datos completos de múltiples productos y comparar sus especificaciones.

```bash
curl -X POST "http://localhost:8082/api/comparisons" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [1, 2, 3],
    "categoryId": "electronics"
  }'
```

**Request DTO: ComparisonRequest**
```json
{
  "productIds": [Long],        // IDs de productos a comparar
  "categoryId": [String]       // Categoría de especificaciones (opcional)
}
```

**Response** (200 OK):
```json
{
  "comparisonId": "comp-12345",
  "products": [
    {
      "id": 1,
      "name": "Laptop Dell XPS 13",
      "description": "Ultrabook with Intel i7",
      "price": 1299.99,
      "rating": 4.8,
      "specifications": [
        {
          "id": 1,
          "name": "Processor",
          "description": "Intel Core i7-13700K",
          "comparable": true
        },
        {
          "id": 2,
          "name": "RAM",
          "description": "16GB DDR5",
          "comparable": true
        }
      ]
    },
    {
      "id": 2,
      "name": "MacBook Pro 14",
      "description": "Apple laptop with M2 Pro",
      "price": 1999.99,
      "rating": 4.9,
      "specifications": [
        {
          "id": 3,
          "name": "Processor",
          "description": "Apple M2 Pro",
          "comparable": true
        },
        {
          "id": 4,
          "name": "RAM",
          "description": "16GB Unified Memory",
          "comparable": true
        }
      ]
    },
    {
      "id": 3,
      "name": "ASUS VivoBook 15",
      "description": "Budget-friendly laptop",
      "price": 649.99,
      "rating": 4.5,
      "specifications": [
        {
          "id": 5,
          "name": "Processor",
          "description": "Intel Core i5-1235U",
          "comparable": true
        },
        {
          "id": 6,
          "name": "RAM",
          "description": "8GB DDR4",
          "comparable": true
        }
      ]
    }
  ],
  "timestamp": "2025-12-09T20:30:00Z"
}
```

## 🔄 Flujo de Orquestación

El Comparison Service realiza los siguientes pasos internamente:

```
1. Recibe ComparisonRequest con productIds y categoryId
   ↓
2. Llama a Product Service → GET /api/products/compare?ids=1&ids=2&ids=3
   ↓
3. Llama a Specification Service → POST /api/specifications/bulk
   ↓
4. Enriquece productos con especificaciones
   ↓
5. Retorna ComparisonResponse con datos completos
```

## 🏗️ Arquitectura: Feign + Resilience4j

El Comparison Service usa:
- **Feign Client**: Para llamadas HTTP declarativas a otros servicios
- **Circuit Breaker** (Resilience4j): Para tolerancia a fallos
- **Service Discovery**: Eureka para encontrar dinámicamente los otros servicios

### Configuración de Tolerancia a Fallos
```yaml
resilience4j:
  circuitbreaker:
    instances:
      productService:
        failureRateThreshold: 50%        # Abre si 50% de llamadas fallan
        waitDurationInOpenState: 10000   # Espera 10s antes de intentar
        slidingWindowSize: 10            # Mira últimas 10 llamadas
      specificationService:
        failureRateThreshold: 50%
        waitDurationInOpenState: 10000
        slidingWindowSize: 10
```

## 🔍 Health & Metrics

```bash
# Health check
curl http://localhost:8082/api/actuator/health

# Application info
curl http://localhost:8082/api/actuator/info

# All metrics
curl http://localhost:8082/api/actuator/metrics

# Circuit breaker status
curl http://localhost:8082/api/actuator/health/circuitbreakers
```

## 🚨 Manejo de Errores

Si Product Service o Specification Service no están disponibles:

```bash
curl -X POST "http://localhost:8082/api/comparisons" \
  -H "Content-Type: application/json" \
  -d '{"productIds": [1, 2], "categoryId": "electronics"}'
```

**Response** (503 Service Unavailable) si los servicios están caídos:
```json
{
  "error": "Service unavailable",
  "message": "Product Service or Specification Service is not responding",
  "timestamp": "2025-12-09T20:35:00Z"
}
```

## 🔗 Dependencias en Eureka

El Comparison Service requiere que estos servicios estén registrados y disponibles en Eureka:

1. **PRODUCT-SERVICE** (puerto 8081)
2. **SPECIFICATION_SERVICE_APPLICATION** (puerto 8083)

Verifica el registro en: http://localhost:8761

## 📋 DTOs

### ComparisonRequest
```json
{
  "productIds": [Long],        // required
  "categoryId": [String]       // optional
}
```

### ComparisonResponse
```json
{
  "comparisonId": String,
  "products": [
    {
      "id": Long,
      "name": String,
      "description": String,
      "price": BigDecimal,
      "rating": Double,
      "specifications": [SpecificationDTO]
    }
  ],
  "timestamp": LocalDateTime
}
```

## 📝 Ejemplos de Casos de Uso

### Comparar 2 laptops en categoría electronics
```bash
curl -X POST "http://localhost:8082/api/comparisons" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [1, 2],
    "categoryId": ["electronics"]
  }'
```

### Comparar 3 smartphones sin filtro de categoría
```bash
curl -X POST "http://localhost:8082/api/comparisons" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [5, 6, 7]
  }'
```

---
**Última actualización**: 09/12/2025

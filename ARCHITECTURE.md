# PruebaMeli — Arquitectura de Microservicios

## 🏗️ Diagrama General

```
                    ┌─────────────────────┐
                    │   Eureka Server     │
                    │   (Puerto 8761)     │
                    │  Service Registry   │
                    └────────────┬────────┘
                                 │
                ┌────────────────┼────────────────┐
                │                │                │
         ┌──────▼──────┐  ┌──────▼──────┐  ┌──────▼──────┐
         │   Product   │  │ Specification│ │ Comparison  │
         │  Service    │  │  Service     │ │  Service    │
         │ (8081)      │  │  (8083)      │ │  (8082)     │
         │             │  │              │ │             │
         │   H2 In-Mem │  │   H2 In-Mem  │ │  Sin BD     │
         │   products  │  │ specifications│ │ (Orquestador)
         └─────────────┘  └──────────────┘ └─────────────┘
                │                │                │
                └────────────────┼────────────────┘
                                 │
                         ┌────────▼────────┐
                         │   Cliente HTTP   │
                         │  (Swagger UI)    │
                         │  (curl/Postman)  │
                         └──────────────────┘
```

## �� Servicios

### 1. **Eureka Server** (Puerto 8761)
- **Rol**: Service Registry / Service Discovery
- **Función**: Registro dinámico de servicios, health checking
- **Base de Datos**: Ninguna (en memoria)
- **Endpoints principales**:
  - `GET http://localhost:8761` — Dashboard
  - `GET http://localhost:8761/eureka/apps` — Lista de aplicaciones registradas

### 2. **Product Service** (Puerto 8081)
- **Rol**: CRUD de productos
- **Base de Datos**: H2 In-Memory (`productdb`)
- **Funcionalidad Principal**:
  - Crear, leer, actualizar, eliminar productos
  - Endpoint de comparación para obtener múltiples productos por IDs
- **Tabla**: `products` (id, name, description, price, imageUrl, rating, categoryId, brandId, available)
- **Endpoints principales**:
  - `GET /api/products` — Obtener todos
  - `GET /api/products/{id}` — Obtener por ID
  - `POST /api/products` — Crear
  - `PUT /api/products/{id}` — Actualizar
  - `DELETE /api/products/{id}` — Eliminar
  - `GET /api/products/compare?ids=1&ids=2` — Comparar múltiples

### 3. **Specification Service** (Puerto 8083)
- **Rol**: Gestión de especificaciones técnicas de productos
- **Base de Datos**: H2 In-Memory (`specificationdb`)
- **Funcionalidad Principal**:
  - Consultar especificaciones de un producto
  - Obtener especificaciones por categoría
  - Soporte para bulk queries (múltiples productos)
- **Tablas**:
  - `specifications` (id, productId, categoryId, name, price, unit, description, comparable)
  - `specification_categories` (id, name, description)
- **Endpoints principales**:
  - `GET /api/specifications/products/{productId}` — Obtener especificaciones
  - `GET /api/specifications/categories` — Obtener categorías
  - `POST /api/specifications/bulk` — Bulk query para múltiples productos

### 4. **Comparison Service** (Puerto 8082)
- **Rol**: Orquestador de comparación de productos
- **Base de Datos**: Ninguna (no almacena, solo orquesta)
- **Funcionalidad Principal**:
  - Orquestar llamadas a Product Service y Specification Service
  - Enriquecer datos de productos con especificaciones
  - Tolerar fallos con Circuit Breaker
- **Patrones**:
  - Feign Client (para llamadas HTTP declarativas)
  - Resilience4j Circuit Breaker (tolerancia a fallos)
  - Service Discovery vía Eureka
- **Endpoints principales**:
  - `POST /api/comparisons` — Comparar productos

## �� Flujo de Comparación

```
Cliente
  │
  └─→ POST /api/comparisons
       {
         "productIds": [1, 2, 3],
         "categoryId": "electronics"
       }
       │
       └─→ [Comparison Service]
            │
            ├─→ Feign Client → Product Service
            │   GET /api/products/compare?ids=1&ids=2&ids=3
            │   ↓ (obtiene lista de productos)
            │
            └─→ Feign Client → Specification Service
                POST /api/specifications/bulk
                {
                  "productIds": [1, 2, 3],
                  "categoryId": ["electronics"]
                }
                ↓ (obtiene especificaciones para cada producto)
            │
            └─→ Enriquece productos con especificaciones
                │
                └─→ Retorna ComparisonResponse
                    {
                      "comparisonId": "...",
                      "products": [
                        {
                          "id": 1,
                          "name": "...",
                          "specifications": [...]
                        }
                      ]
                    }
                    │
                    └─→ Cliente recibe respuesta
```

## 🗄️ Bases de Datos

### Product Service — H2 In-Memory
```
Database: productdb
User: sa
Password: password
JDBC: jdbc:h2:mem:productdb

Tables:
  ├─ products
  │  ├─ id (BIGINT, PK)
  │  ├─ name (VARCHAR)
  │  ├─ description (VARCHAR)
  │  ├─ price (DECIMAL)
  │  ├─ image_url (VARCHAR)
  │  ├─ rating (DOUBLE)
  │  ├─ category_id (BIGINT)
  │  ├─ brand_id (BIGINT)
  │  └─ available (BOOLEAN)
```

### Specification Service — H2 In-Memory
```
Database: specificationdb
User: sa
Password: password
JDBC: jdbc:h2:mem:specificationdb

Tables:
  ├─ specifications
  │  ├─ id (BIGINT, PK)
  │  ├─ product_id (BIGINT)
  │  ├─ category_id (VARCHAR, FK)
  │  ├─ name (VARCHAR)
  │  ├─ price (VARCHAR)
  │  ├─ unit (VARCHAR)
  │  ├─ description (VARCHAR)
  │  └─ comparable (BOOLEAN)
  │
  └─ specification_categories
     ├─ id (VARCHAR, PK)
     ├─ name (VARCHAR)
     └─ description (VARCHAR)
```

## 🔐 Configuración de Tolerancia a Fallos

El **Comparison Service** usa **Resilience4j Circuit Breaker**:

```yaml
resilience4j:
  circuitbreaker:
    instances:
      productService:
        failureRateThreshold: 50%          # Abre si falla >50%
        waitDurationInOpenState: 10000     # Espera 10s
        slidingWindowSize: 10              # Analiza últimas 10 llamadas
        
      specificationService:
        failureRateThreshold: 50%
        waitDurationInOpenState: 10000
        slidingWindowSize: 10
```

**Estados del Circuit Breaker:**
1. **CLOSED** — Tráfico normal
2. **OPEN** — Rechaza llamadas después de X fallos
3. **HALF_OPEN** — Permite 1-2 llamadas de prueba antes de cerrar/abrir

## 📊 Monitoreo y Observabilidad

### Actuator Endpoints (en todos los servicios)
```bash
/actuator/health           # Health check
/actuator/info            # Info de aplicación
/actuator/metrics         # Métricas de JVM, requests, etc.
/actuator/prometheus      # Formato Prometheus
```

### Swagger UI (en cada servicio)
```
Product Service:      http://localhost:8081/api/swagger-ui.html
Specification Service: http://localhost:8083/api/swagger-ui.html
Comparison Service:    http://localhost:8082/api/swagger-ui.html
```

### Eureka Dashboard
```
http://localhost:8761
```

## 🚀 Stack Tecnológico

| Componente | Versión |
|-----------|---------|
| Spring Boot | 4.0.0 (Eureka), 3.3.5 (otros) |
| Spring Cloud | Eureka Client/Server |
| Netflix Eureka | 2.0.5 |
| Resilience4j | Integrado con Spring Cloud |
| Feign Client | Spring Cloud OpenFeign |
| H2 Database | In-Memory |
| SpringDoc OpenAPI | Swagger UI + API Docs |
| Lombok | Anotaciones |
| JPA/Hibernate | ORM |
| Maven | 3.8+ |
| Java | 21 |

## 🔄 Flujo de Inicio

```
1. Inicia Eureka Server (8761)
   ↓
2. Espera a que Eureka esté listo
   ↓
3. Inicia Product Service (8081)
   → Se registra en Eureka
   ↓
4. Inicia Specification Service (8083)
   → Se registra en Eureka
   ↓
5. Inicia Comparison Service (8082)
   → Se registra en Eureka
   → Detecta Product y Specification via Eureka
   ↓
6. Sistema listo para recibir solicitudes
```

## 🆘 Troubleshooting

### Servicio no se registra en Eureka
- Verifica que Eureka esté arriba (`http://localhost:8761`)
- Revisa logs del servicio: `tail -f <service>.log`
- Busca errores de conexión

### Circuit Breaker abierto (Comparison fallaen)
- Verifica health de Product/Specification: `curl http://localhost:808X/api/actuator/health`
- Si están DOWN, espera 10s y reinténtalo
- Revisa logs del Comparison Service

### Puerto ya en uso
```bash
lsof -i :PORT
kill -9 PID
```

### BD H2 no carga datos
- H2 In-Memory = datos perdidos al reiniciar
- Para persistencia, cambiar a PostgreSQL/MySQL en `application.yml`

---
**Última actualización**: 09/12/2025

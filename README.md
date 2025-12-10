# PruebaMeli — Sistema de Gestión y Comparación de Productos

Plataforma de microservicios para gestionar, consultar especificaciones y comparar productos. Construida con **Spring Boot 4.x / 3.3.x** y **Netflix Eureka** para descubrimiento de servicios dinámico.

## Descripción del Proyecto

Este sistema de microservicios está diseñado para manejar:
- **Gestión de productos** (catálogo, CRUD)
- **Especificaciones de productos** (características técnicas, categorías)
- **Comparación de productos** (análisis lado a lado)
- **Descubrimiento dinámico de servicios** (Eureka Registry)

Ideal para plataformas e-commerce, marketplaces o sistemas que requieran gestionar grandes catálogos de productos con capacidad de comparación.

## Arquitectura

### Servicios

| Servicio | Puerto | BD | Descripción |
|----------|--------|----|----|
| **eureka-server** | 8761 | N/A | Servidor de descubrimiento (service registry) |
| **product-service-application** | 8081 | H2 In-Memory | CRUD de productos, información básica (nombre, descripción, precio, rating) |
| **specification_service_application** | 8083 | H2 In-Memory | Gestión de especificaciones técnicas de productos, categorías de specs |
| **comparison_service_appplication** | 8082 | N/A (Orquestador) | Orquesta comparaciones entre productos usando Product + Specification |

### Stack Tecnológico

- **Framework**: Spring Boot 4.0.0 (Eureka), Spring Boot 3.3.5 (otros servicios)
- **Service Discovery**: Netflix Eureka Client/Server
- **BD**: H2 In-Memory con JPA/Hibernate
- **API Docs**: SpringDoc OpenAPI (Swagger)
- **Resilience**: Resilience4j, Feign Client con Circuit Breaker
- **Monitoring**: Spring Actuator (health, metrics, prometheus)
- **Build**: Maven (mvnw)

## Inicio Rápido

### Requisitos
- Java 21+
- Maven 3.8+ (incluido en los wrappers)
- Bash/Zsh

### 1. Arrancar todos los servicios

```bash
./scripts/start-all.sh
```

**Qué ocurre:**
- Inicia `eureka-server` y espera a que esté disponible
- Luego inicia los 3 servicios en background
- Los logs se guardan en: `<service>/service.log`

### 2. Acceder a los servicios

- **Dashboard Eureka**: http://localhost:8761
- **Product Service (Swagger)**: http://localhost:8081/api/swagger-ui.html
- **Specification Service (Swagger)**: http://localhost:8083/api/swagger-ui.html
- **Comparison Service (Swagger)**: http://localhost:8082/api/swagger-ui.html

### 3. Detener los servicios

```bash
./scripts/stop-all.sh
```

## Base de Datos

### Product Service (H2 In-Memory)
- **URL**: `jdbc:h2:mem:productdb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Console**: http://localhost:8081/api/h2-console

**Tabla: `products`**
- `id` (BIGINT, PK)
- `name` (VARCHAR, NOT NULL)
- `description` (VARCHAR, NOT NULL)
- `price` (DECIMAL, NOT NULL)
- `image_url` (VARCHAR)
- `rating` (DOUBLE)
- `category_id` (BIGINT)
- `brand_id` (BIGINT)
- `available` (BOOLEAN)

### Specification Service (H2 In-Memory)
- **URL**: `jdbc:h2:mem:specificationdb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Console**: http://localhost:8083/api/h2-console

**Tablas:**
- `specifications`: especificaciones técnicas (id, productId, categoryId, name, price, unit, description, comparable)
- `specification_categories`: categorías de especificaciones (id, name, description)

### Comparison Service
- No tiene BD propia; orquesta llamadas a Product y Specification via Feign Client

##  APIs y Ejemplos

Consulta los archivos **`API_EXAMPLES.md`** en cada carpeta de servicio para:
- Ejemplos completos de **curl**
- Payloads de request/response
- Códigos de error y solución de problemas

- [Product Service Examples](./product-service-application/API_EXAMPLES.md)
- [Specification Service Examples](./specification_service_application/API_EXAMPLES.md)
- [Comparison Service Examples](./comparison_service_appplication/API_EXAMPLES.md)

## Monitoreo y Logs

### Ver logs en vivo

```bash
# Product Service
tail -f product-service-application/product.log

# Specification Service
tail -f specification_service_application/specification.log

# Comparison Service
tail -f comparison_service_appplication/comparison.log

# Eureka Server
tail -f eureka-server/eureka.log
```

### Health Checks

```bash
curl http://localhost:8081/api/actuator/health  # Product
curl http://localhost:8083/api/actuator/health  # Specification
curl http://localhost:8082/api/actuator/health  # Comparison
```

### Metrics (Prometheus)

```bash
curl http://localhost:8081/api/actuator/metrics       # Product
curl http://localhost:8083/api/actuator/metrics       # Specification
curl http://localhost:8082/api/actuator/metrics       # Comparison
```

## Configuración y Puertos

Para cambiar puertos o perfiles, edita los archivos `application.yml`/`application.properties` en cada servicio o usa variables de entorno:

```bash
SPRING_APPLICATION_JSON='{"server":{"port":8085}}' ./mvnw spring-boot:run
```

## Troubleshooting

| Problema | Solución |
|----------|----------|
| Servicios no se registran en Eureka | Revisa los logs (`tail -f <service>.log`). Asegúrate de que Eureka esté arriba (`localhost:8761`) |
| Puerto ya en uso | Mata procesos con `lsof -ti :PORT \| xargs kill -9` o cambia el puerto en `application.yml` |
| H2 Console no carga | Accede con usuario `sa` y contraseña `password`. Si falla, revisa que la BD esté inicializada. |
| Comparison fallaen al llamar servicios | Verifica que Product y Specification estén registrados en Eureka dashboard |

## 📝 Scripts

- `scripts/start-all.sh` — Inicia todos los servicios con logs
- `scripts/stop-all.sh` — Detiene servicios por puerto

## Licencia

Proyecto de prueba (Test). Libre de usar para aprendizaje.

---

**Generado**: 09/12/2025 | **Última actualización**: Scripts y documentación de API


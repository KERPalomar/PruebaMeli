# 📚 Documentación — PruebaMeli

Bienvenido a la documentación completa del sistema de microservicios PruebaMeli.

## 📖 Estructura de Documentación

```
📁 PruebaMeli/
├── 📄 README.md                          ← START HERE (Guía principal)
├── 📄 ARCHITECTURE.md                    ← Diagrama y diseño del sistema
├── 📄 DOCUMENTATION.md                   ← Este archivo
├── 📁 scripts/
│   ├── start-all.sh                      ← Inicia todos los servicios
│   └── stop-all.sh                       ← Detiene todos los servicios
├── 📁 eureka-server/
│   └── Service Registry (puerto 8761)
├── 📁 product-service-application/
│   ├── pom.xml
│   └── API_EXAMPLES.md                   ← Ejemplos de curl para Product
├── 📁 specification_service_application/
│   ├── pom.xml
│   └── API_EXAMPLES.md                   ← Ejemplos de curl para Specification
└── 📁 comparison_service_appplication/
    ├── pom.xml
    └── API_EXAMPLES.md                   ← Ejemplos de curl para Comparison
```

## 🚀 Inicio Rápido

```bash
# 1. Clonar y navegar
cd /Users/gabriel/Desktop/PruebaMeli

# 2. Arrancar todos los servicios
./scripts/start-all.sh

# 3. Verificar en Eureka Dashboard
open http://localhost:8761

# 4. Hacer una prueba
curl -X GET "http://localhost:8081/api/products"
```

## �� Archivos Clave

### README.md
- 📝 Descripción general del proyecto
- 🚀 Cómo arrancar y detener servicios
- 🗄️ Información sobre bases de datos (H2)
- 🔍 Links a ejemplos de API

### ARCHITECTURE.md
- 🏗️ Diagrama de microservicios
- 🔄 Flujos de datos y orquestación
- 🗄️ Esquema completo de bases de datos
- 🔐 Tolerancia a fallos (Circuit Breaker)
- 📊 Stack tecnológico

### API_EXAMPLES.md (en cada carpeta)
- 📝 Ejemplos de curl para cada endpoint
- 📋 DTOs y modelos de request/response
- 🗄️ Acceso a consoles H2
- 🔍 Health checks y métricas

## 🎯 Casos de Uso

### 1. Crear un Producto
```bash
curl -X POST "http://localhost:8081/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Dell XPS 13",
    "description": "Ultrabook...",
    "price": 1299.99,
    ...
  }'
```
👉 Ver detalles en `product-service-application/API_EXAMPLES.md`

### 2. Obtener Especificaciones
```bash
curl -X GET "http://localhost:8083/api/specifications/products/1"
```
👉 Ver detalles en `specification_service_application/API_EXAMPLES.md`

### 3. Comparar Productos
```bash
curl -X POST "http://localhost:8082/api/comparisons" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [1, 2, 3],
    "categoryId": "electronics"
  }'
```
👉 Ver detalles en `comparison_service_appplication/API_EXAMPLES.md`

## 🔌 Puertos y URLs

| Servicio | Puerto | URL |
|----------|--------|-----|
| **Eureka Dashboard** | 8761 | http://localhost:8761 |
| **Product Service** | 8081 | http://localhost:8081/api |
| **Specification Service** | 8083 | http://localhost:8083/api |
| **Comparison Service** | 8082 | http://localhost:8082/api |

## 🗄️ Bases de Datos

Todas las BDs son **H2 In-Memory** con credenciales por defecto:
- **Usuario**: `sa`
- **Contraseña**: `password`

| Servicio | BD | Console |
|----------|----|----|
| Product | productdb | http://localhost:8081/api/h2-console |
| Specification | specificationdb | http://localhost:8083/api/h2-console |
| Comparison | (sin BD) | N/A |

## 🆘 Solución de Problemas

### Servicios no inician
1. Revisa que puerto 8761 esté libre
2. Revisa logs: `tail -f <service>/<service>.log`
3. Verifica Java 21+: `java -version`

### Eureka muestra servicios como DOWN
- Espera 30 segundos para que se registren
- Revisa `/actuator/health` de cada servicio

### BD vacía después de reiniciar
- H2 In-Memory = datos se pierden
- Para persistencia, cambiar a PostgreSQL/MySQL

## 📚 Recursos Adicionales

- **Swagger UI** (en cada servicio): `/api/swagger-ui.html`
- **OpenAPI Spec**: `/api/v3/api-docs`
- **Actuator**: `/api/actuator` (health, metrics, info)

## 🤝 Estructura del Código

```
src/main/java/com/mercadolibre/comparisontest/
├── product/
│   ├── controller/        # REST Controllers
│   ├── model/            # Entities y DTOs
│   ├── repository/       # Spring Data JPA
│   ├── service/          # Business logic
│   └── exception/        # Exception handlers
├── specification/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── exception/
└── comparison/
    ├── controller/
    ├── model/
    ├── service/
    ├── feign/           # Feign clients (para llamar otros servicios)
    └── exception/
```

## 📖 Patrones Implementados

1. **Service Discovery** (Eureka) — Registro dinámico de servicios
2. **Circuit Breaker** (Resilience4j) — Tolerancia a fallos
3. **Feign Client** — Llamadas HTTP declarativas entre servicios
4. **Repository Pattern** (Spring Data JPA) — Acceso a datos
5. **DTO Pattern** — Separación entre modelos y DTOs
6. **Exception Handling** — Manejo centralizado de excepciones

## ✅ Checklist de Setup

- [ ] Java 21+ instalado
- [ ] Scripts ejecutables: `chmod +x scripts/*.sh`
- [ ] Ejecutar `./scripts/start-all.sh`
- [ ] Verificar Eureka: http://localhost:8761
- [ ] Probar Product Service: `curl http://localhost:8081/api/products`
- [ ] Probar Specification Service: `curl http://localhost:8083/api/specifications/categories`
- [ ] Probar Comparison Service: `curl -X POST http://localhost:8082/api/comparisons ...`

## 📞 Soporte

Si tienes problemas:
1. Revisa `README.md` — Guía de inicio
2. Revisa `ARCHITECTURE.md` — Entiende cómo funciona
3. Revisa `API_EXAMPLES.md` de cada servicio — Ejemplos de curl
4. Revisa logs: `tail -f <service>.log`
5. Verifica health: `curl http://localhost:808X/api/actuator/health`

---

**Sistema actualizado**: 09/12/2025  
**Versión**: 1.0 — Documentación completa

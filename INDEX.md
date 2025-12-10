# 📚 Índice de Documentación — PruebaMeli

Bienvenido. Esta es la **guía completa** para navegar toda la documentación del proyecto PruebaMeli.

## 🎯 ¿POR DÓNDE EMPIEZO?

**Si tienes 5 minutos**: Lee [`README.md`](./README.md)  
**Si tienes 2 minutos**: Lee [`QUICK_REFERENCE.md`](./QUICK_REFERENCE.md)  
**Si quieres entender todo**: Lee [`DOCUMENTATION.md`](./DOCUMENTATION.md)  

---

## 📚 Documentos en Orden de Lectura

### 1. 📄 [`README.md`](./README.md) ⭐ **EMPIEZA AQUÍ**
**Tiempo**: ~5 minutos  
**Contenido**:
- Descripción general del proyecto
- Cómo arrancar servicios
- Información sobre bases de datos (H2)
- Tabla de servicios y puertos
- Links a ejemplos de API
- Troubleshooting

### 2. ⚡ [`QUICK_REFERENCE.md`](./QUICK_REFERENCE.md) 
**Tiempo**: ~2 minutos  
**Contenido**:
- Comandos rápidos y listos para copy-paste
- URLs importantes
- Ejemplos de curl
- Puertos y credenciales
- Troubleshooting rápido

### 3. 🏗️ [`ARCHITECTURE.md`](./ARCHITECTURE.md)
**Tiempo**: ~10 minutos  
**Contenido**:
- Diagrama del sistema
- Descripción detallada de cada servicio
- Flujo de comparación
- Esquemas SQL completos
- Configuración de Circuit Breaker
- Stack tecnológico

### 4. 📚 [`DOCUMENTATION.md`](./DOCUMENTATION.md)
**Tiempo**: ~15 minutos  
**Contenido**:
- Índice centralizado
- Estructura de carpetas
- Patrones implementados
- Recursos adicionales
- Checklist de setup

---

## 🔧 Documentación por Servicio

### Product Service (Puerto 8081)
**Archivo**: [`product-service-application/API_EXAMPLES.md`](./product-service-application/API_EXAMPLES.md)

**Incluye**:
- GET /api/products
- GET /api/products/{id}
- POST /api/products
- PUT /api/products/{id}
- DELETE /api/products/{id}
- GET /api/products/compare
- Acceso a H2 Console
- Health & Metrics

### Specification Service (Puerto 8083)
**Archivo**: [`specification_service_application/API_EXAMPLES.md`](./specification_service_application/API_EXAMPLES.md)

**Incluye**:
- GET /api/specifications/products/{productId}
- GET /api/specifications/products/{id}/categories/{categoryId}
- POST /api/specifications/bulk
- GET /api/specifications/categories
- GET /api/specifications/categories/{categoryId}
- Acceso a H2 Console
- Esquemas de tablas

### Comparison Service (Puerto 8082)
**Archivo**: [`comparison_service_appplication/API_EXAMPLES.md`](./comparison_service_appplication/API_EXAMPLES.md)

**Incluye**:
- POST /api/comparisons
- Flujo de orquestación
- Arquitectura Feign + Resilience4j
- Manejo de errores
- Dependencias en Eureka

---

## 🚀 Inicio Rápido

```bash
# 1. Clonar repositorio (ya estás aquí)
cd /Users/gabriel/Desktop/PruebaMeli

# 2. Arrancar todos los servicios
./scripts/start-all.sh

# 3. Verificar Eureka
open http://localhost:8761

# 4. Detener servicios
./scripts/stop-all.sh
```

---

## 🌐 URLs Importantes

| Recurso | URL |
|---------|-----|
| **Eureka Dashboard** | http://localhost:8761 |
| **Product API** | http://localhost:8081/api |
| **Specification API** | http://localhost:8083/api |
| **Comparison API** | http://localhost:8082/api |
| **Product Swagger** | http://localhost:8081/api/swagger-ui.html |
| **Spec Swagger** | http://localhost:8083/api/swagger-ui.html |
| **Comparison Swagger** | http://localhost:8082/api/swagger-ui.html |

---

## 🗄️ Bases de Datos

**Credenciales** (en todos):
- **Usuario**: `sa`
- **Contraseña**: `password`

| Servicio | Consola | JDBC |
|----------|---------|------|
| **Product** | http://localhost:8081/api/h2-console | `jdbc:h2:mem:productdb` |
| **Specification** | http://localhost:8083/api/h2-console | `jdbc:h2:mem:specificationdb` |

---

## 📌 Estructura del Proyecto

```
PruebaMeli/
├── 📄 README.md              ← Empieza aquí
├── ⚡ QUICK_REFERENCE.md      ← Referencia rápida
├── 🏗️  ARCHITECTURE.md        ← Diagramas y diseño
├── 📚 DOCUMENTATION.md       ← Documentación completa
├── 📋 INDEX.md               ← Este archivo
├── 📁 scripts/
│   ├── start-all.sh          ← Iniciar servicios
│   └── stop-all.sh           ← Detener servicios
├── 📁 eureka-server/         ← Registro de servicios
├── 📁 product-service-application/
│   └── API_EXAMPLES.md
├── 📁 specification_service_application/
│   └── API_EXAMPLES.md
└── 📁 comparison_service_appplication/
    └── API_EXAMPLES.md
```

---

## 🎯 Mapa Mental Rápido

```
PruebaMeli = Sistema de 4 microservicios
│
├─ Eureka Server (8761)
│  └─ Descubrimiento dinámico de servicios
│
├─ Product Service (8081)
│  └─ CRUD de productos (BD: H2 productdb)
│
├─ Specification Service (8083)
│  └─ Especificaciones técnicas (BD: H2 specificationdb)
│
└─ Comparison Service (8082)
   └─ Orquesta comparaciones con Feign + Resilience4j
```

---

## 📚 Recursos Adicionales

- **Swagger UI**: Disponible en cada servicio (`/api/swagger-ui.html`)
- **Health Checks**: `/api/actuator/health`
- **Métricas**: `/api/actuator/metrics`
- **API Docs**: `/api/v3/api-docs`

---

## 🎓 Nivel de Dificultad por Documento

| Documento | Principiante | Intermedio | Avanzado |
|-----------|:------------:|:----------:|:--------:|
| QUICK_REFERENCE.md | ✅ | ✅ | ✅ |
| README.md | ✅ | ✅ | ✅ |
| API_EXAMPLES.md | ✅ | ✅ | ⭐ |
| ARCHITECTURE.md | ⭐ | ✅ | ✅ |
| DOCUMENTATION.md | ⭐ | ✅ | ✅ |

✅ = Recomendado  
⭐ = Desafiante pero valioso

---

## 🆘 Troubleshooting Rápido

### "No sé por dónde empezar"
→ Lee [`README.md`](./README.md) (5 minutos)

### "Quiero comandos rápidos"
→ Lee [`QUICK_REFERENCE.md`](./QUICK_REFERENCE.md) (2 minutos)

### "Quiero entender la arquitectura"
→ Lee [`ARCHITECTURE.md`](./ARCHITECTURE.md) (10 minutos)

### "Quiero ejemplos de API"
→ Lee [`API_EXAMPLES.md`](./product-service-application/API_EXAMPLES.md) de cada servicio

### "Tengo un error"
→ Busca en [`DOCUMENTATION.md`](./DOCUMENTATION.md) sección de troubleshooting

---

## 📞 Resumen

```
┌──────────────────────────────────────────────────────────────┐
│ START HERE:  README.md                                       │
│              ./scripts/start-all.sh                          │
│              http://localhost:8761                           │
└──────────────────────────────────────────────────────────────┘
```

---

## ✅ Checklist de Lectura Recomendada

- [ ] Leer README.md (5 min)
- [ ] Ejecutar ./scripts/start-all.sh
- [ ] Ver Eureka Dashboard (localhost:8761)
- [ ] Leer QUICK_REFERENCE.md (2 min)
- [ ] Probar curl ejemplo de Product Service
- [ ] Leer ARCHITECTURE.md (10 min)
- [ ] Leer DOCUMENTATION.md (15 min)
- [ ] Explorar API_EXAMPLES.md de cada servicio
- [ ] Abrir Swagger UI en cada servicio

---

**Última actualización**: 09 de Diciembre, 2025

---

**¡Bienvenido a PruebaMeli! 🚀**

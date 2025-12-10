# Specification Service — API Examples

**Puerto**: 8083  
**Context Path**: `/api`  
**Base URL**: `http://localhost:8083/api`  
**BD**: H2 In-Memory (`specificationdb`)  
**Swagger**: http://localhost:8083/api/swagger-ui.html

## 📝 Endpoints

### 1. Obtener especificaciones por Producto ID

```bash
curl -X GET "http://localhost:8083/api/specifications/products/1" \
  -H "Content-Type: application/json"
```

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "productId": 1,
    "categoryId": "electronics",
    "name": "Processor",
    "price": "999",
    "unit": "units",
    "description": "Intel Core i7-13700K",
    "comparable": true
  },
  {
    "id": 2,
    "productId": 1,
    "categoryId": "electronics",
    "name": "RAM",
    "price": "200",
    "unit": "GB",
    "description": "16GB DDR5",
    "comparable": true
  }
]
```

### 2. Obtener especificaciones por Producto y Categoría

```bash
curl -X GET "http://localhost:8083/api/specifications/products/1/categories/electronics" \
  -H "Content-Type: application/json"
```

**Response** (200 OK): Lista de especificaciones filtradas por categoría

### 3. Obtener especificaciones en lote (Bulk)

```bash
curl -X POST "http://localhost:8083/api/specifications/bulk" \
  -H "Content-Type: application/json" \
  -d '{
    "productIds": [1, 2, 3],
    "categoryId": ["electronics"]
  }'
```

**Response** (200 OK):
```json
{
  "specifications": {
    "1": [
      {
        "id": 1,
        "productId": 1,
        "categoryId": "electronics",
        "name": "Processor",
        "description": "Intel Core i7-13700K",
        "comparable": true
      },
      {
        "id": 2,
        "productId": 1,
        "categoryId": "electronics",
        "name": "RAM",
        "description": "16GB DDR5",
        "comparable": true
      }
    ],
    "2": [
      {
        "id": 3,
        "productId": 2,
        "categoryId": "electronics",
        "name": "Processor",
        "description": "Apple M2",
        "comparable": true
      }
    ]
  }
}
```

### 4. Obtener todas las categorías

```bash
curl -X GET "http://localhost:8083/api/specifications/categories" \
  -H "Content-Type: application/json"
```

**Response** (200 OK):
```json
[
  {
    "id": "electronics",
    "name": "Electronics",
    "description": "Electronic specifications"
  },
  {
    "id": "performance",
    "name": "Performance",
    "description": "Performance metrics"
  },
  {
    "id": "design",
    "name": "Design",
    "description": "Design and appearance"
  }
]
```

### 5. Obtener categoría por ID

```bash
curl -X GET "http://localhost:8083/api/specifications/categories/electronics" \
  -H "Content-Type: application/json"
```

**Response** (200 OK):
```json
{
  "id": "electronics",
  "name": "Electronics",
  "description": "Electronic specifications"
}
```

## 🗄️ Base de Datos

### H2 In-Memory Console
- **URL**: http://localhost:8083/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:specificationdb`
- **User**: `sa` / **Password**: `password`

### Tabla: `specifications`
```sql
CREATE TABLE specifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  product_id BIGINT NOT NULL,
  category_id VARCHAR(255),
  name VARCHAR(255) NOT NULL,
  price VARCHAR(255),
  unit VARCHAR(255),
  description VARCHAR(255),
  comparable BOOLEAN
);
```

### Tabla: `specification_categories`
```sql
CREATE TABLE specification_categories (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255)
);
```

## 🔍 Health & Metrics

```bash
curl http://localhost:8083/api/actuator/health
curl http://localhost:8083/api/actuator/info
curl http://localhost:8083/api/actuator/metrics
```

## 📋 DTOs

### SpecificationDTO
```json
{
  "id": Long,
  "productId": Long,
  "categoryId": String,
  "name": String,
  "price": String,
  "unit": String,
  "description": String,
  "comparable": boolean
}
```

### SpecificationCategoryDTO
```json
{
  "id": String,
  "name": String,
  "description": String
}
```

---
**Última actualización**: 09/12/2025

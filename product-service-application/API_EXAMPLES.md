# Product Service — API Examples

**Puerto**: 8081  
**Context Path**: `/api`  
**Base URL**: `http://localhost:8081/api`  
**BD**: H2 In-Memory (`productdb`)  
**Swagger**: http://localhost:8081/api/swagger-ui.html

## 📝 Endpoints

### 1. Obtener todos los productos

```bash
curl -X GET "http://localhost:8081/api/products" \
  -H "Content-Type: application/json"
```

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "name": "Laptop Dell XPS 13",
    "description": "Ultrabook with Intel i7, 16GB RAM",
    "price": 1299.99,
    "imageUrl": "https://example.com/images/laptop.jpg",
    "rating": 4.8,
    "categoryId": 1,
    "brandId": 101,
    "available": true
  },
  {
    "id": 2,
    "name": "iPhone 15",
    "description": "Apple smartphone latest model",
    "price": 999.99,
    "imageUrl": "https://example.com/images/iphone.jpg",
    "rating": 4.9,
    "categoryId": 2,
    "brandId": 102,
    "available": true
  }
]
```

### 2. Obtener un producto por ID

```bash
curl -X GET "http://localhost:8081/api/products/1" \
  -H "Content-Type: application/json"
```

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Laptop Dell XPS 13",
  "description": "Ultrabook with Intel i7, 16GB RAM",
  "price": 1299.99,
  "imageUrl": "https://example.com/images/laptop.jpg",
  "rating": 4.8,
  "categoryId": 1,
  "brandId": 101,
  "available": true
}
```

### 3. Crear un producto (POST)

```bash
curl -X POST "http://localhost:8081/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Samsung Galaxy S24",
    "description": "5G Android smartphone with 120Hz display",
    "price": 899.99,
    "imageUrl": "https://example.com/images/galaxy.jpg",
    "rating": 4.7,
    "categoryId": 2,
    "brandId": 103,
    "available": true
  }'
```

**Response** (201 Created):
```json
{
  "id": 3,
  "name": "Samsung Galaxy S24",
  "description": "5G Android smartphone with 120Hz display",
  "price": 899.99,
  "imageUrl": "https://example.com/images/galaxy.jpg",
  "rating": 4.7,
  "categoryId": 2,
  "brandId": 103,
  "available": true
}
```

### 4. Actualizar un producto (PUT)

```bash
curl -X PUT "http://localhost:8081/api/products/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Dell XPS 13 Plus",
    "description": "Updated ultrabook with Intel i9",
    "price": 1499.99,
    "imageUrl": "https://example.com/images/laptop-updated.jpg",
    "rating": 4.9,
    "categoryId": 1,
    "brandId": 101,
    "available": true
  }'
```

### 5. Eliminar un producto (DELETE)

```bash
curl -X DELETE "http://localhost:8081/api/products/1" \
  -H "Content-Type: application/json"
```

**Response** (204 No Content)

### 6. Comparar productos (GET /compare)

```bash
curl -X GET "http://localhost:8081/api/products/compare?ids=1&ids=2&ids=3" \
  -H "Content-Type: application/json"
```

## 🗄️ Base de Datos

### H2 In-Memory Console
- **URL**: http://localhost:8081/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:productdb`
- **User**: `sa` / **Password**: `password`

## 🔍 Health & Metrics

```bash
curl http://localhost:8081/api/actuator/health
curl http://localhost:8081/api/actuator/info
curl http://localhost:8081/api/actuator/metrics
```

---
**Última actualización**: 09/12/2025

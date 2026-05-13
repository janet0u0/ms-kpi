MS-KPI - Grupo Cordillera

Microservicio de gestión de indicadores clave de desempeño (KPIs) del Grupo Cordillera.

## Tecnologías
- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Actuator
- MySQL 8.0
- Docker
- Lombok
- Maven

## Patrones Aplicados
- **Repository Pattern**: Abstrae el acceso a la base de datos
- **DTO Pattern**: Separa el modelo interno de la API
- **Builder Pattern**: Construcción de entidades con Lombok @Builder

## Requisitos
- Java 17
- Docker Desktop
- Maven

## Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone 
cd ms-kpi
```

### 2. Levantar MySQL con Docker
```bash
docker-compose up -d
```

### 3. Ejecutar el microservicio
```bash
.\mvnw spring-boot:run
```

El servicio quedará disponible en `http://localhost:8082`

## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/kpis | Listar todos los KPIs |
| GET | /api/kpis/{id} | Buscar KPI por ID |
| GET | /api/kpis/tipo/{tipo} | KPIs por tipo |
| GET | /api/kpis/area/{area} | KPIs por área |
| POST | /api/kpis | Crear nuevo KPI |
| PUT | /api/kpis/{id} | Actualizar KPI |
| DELETE | /api/kpis/{id} | Eliminar KPI |

## Ejemplo de uso

### Crear KPI
```json
POST /api/kpis
{
    "tipo": "VENTAS",
    "valor": 1000000.00,
    "fecha": "2026-05-07",
    "area": "COMERCIAL",
    "estado": "VERDE"
}
```

### Tipos disponibles
| Tipo | Descripción |
|------|-------------|
| VENTAS | KPI de ventas |
| RENTABILIDAD | KPI de rentabilidad |
| INVENTARIO | KPI de inventario |
| LOGISTICA | KPI de logística |

### Estados disponibles
| Estado | Descripción |
|--------|-------------|
| VERDE | Óptimo |
| AMARILLO | Precaución |
| ROJO | Crítico |

## Estructura del proyecto
ms-kpi/
├── src/
│   ├── main/
│   │   ├── java/com/cordillera/mskpi/
│   │   │   ├── controller/
│   │   │   │   └── KpiController.java
│   │   │   ├── dto/
│   │   │   │   ├── KpiRequestDTO.java
│   │   │   │   └── KpiResponseDTO.java
│   │   │   ├── exception/
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   └── Kpi.java
│   │   │   ├── repository/
│   │   │   │   └── KpiRepository.java
│   │   │   └── service/
│   │   │       └── KpiService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md

## Monitoreo
```
GET http://localhost:8082/actuator/health
GET http://localhost:8082/actuator/info
```

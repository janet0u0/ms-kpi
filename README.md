# MS-KPI - Grupo Cordillera

## 📌 Descripción
Microservicio de Gestión de Indicadores KPI.
Calcula y gestiona métricas estratégicas en tiempo real para
la Plataforma de Monitoreo Inteligente del Grupo Cordillera.

## 🎯 Patrón aplicado
- **Repository Pattern**: Abstrae el acceso a la base de datos,
  permitiendo cambiar el motor de BD sin afectar la lógica de negocio.

## ⚙️ Tecnologías
- Java 17
- Spring Boot 3.5.14
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## 📁 Estructura del proyecto
ms-kpi/
├── controller/   → KpiController (endpoints REST)
├── service/      → KpiService (lógica de negocio)
├── repository/   → KpiRepository (acceso a datos)
├── model/        → Kpi (entidad JPA)
└── dto/          → KpiRequestDTO, KpiResponseDTO

## 📊 Tipos de KPI disponibles
| Tipo | Descripción |
|------|-------------|
| VENTAS | Métricas de ventas por sucursal |
| RENTABILIDAD | Indicadores financieros |
| INVENTARIO | Estado del stock |
| LOGISTICA | Métricas operativas |

## 🌐 Endpoints disponibles
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/kpis | Lista todos los KPIs |
| GET | /api/kpis/{id} | Busca KPI por ID |
| GET | /api/kpis/tipo/{tipo} | KPIs por tipo |
| GET | /api/kpis/area/{area} | KPIs por área |
| POST | /api/kpis | Crea nuevo KPI |
| PUT | /api/kpis/{id} | Actualiza KPI |
| DELETE | /api/kpis/{id} | Elimina KPI |

## 📦 Ejemplo POST /api/kpis
```json
{
  "tipo": "VENTAS",
  "valor": 125000.00,
  "fecha": "2026-04-30",
  "area": "FINANZAS",
  "estado": "AMARILLO"
}
```

## 🐳 Cómo ejecutar con Docker
```bash
docker-compose up -d
mvn spring-boot:run
```

## 💻 Cómo ejecutar sin Docker
1. Iniciar MySQL (XAMPP)
2. Crear base de datos: `ms_kpi_db`
3. Ejecutar: `mvn spring-boot:run`
4. Servidor en: http://localhost:8082

## ✅ Requisitos
- Java 17+
- Maven
- MySQL 8.0 o Docker

## 👥 Autores
- Janet Huaylla Huayllas
- Bairo Pasten Codoceo
# MS-KPI - Grupo Cordillera

## Descripción
Microservicio de Gestión de Indicadores KPI.
Calcula métricas estratégicas en tiempo real (ventas, rentabilidad, logística).

## Tecnologías
- Java 17
- Spring Boot
- Maven

## KPIs disponibles
- Ventas totales vs meta
- Porcentaje de cumplimiento
- Rentabilidad neta
- Stock crítico

## Endpoints principales
- GET /api/kpis
- GET /api/kpis/sucursal/{id}
- POST /api/kpis/calcular

## Cómo ejecutar
mvn spring-boot:run

## Autores
- Janet Huaylla Huayllas
- Bairo Pasten Codoceo
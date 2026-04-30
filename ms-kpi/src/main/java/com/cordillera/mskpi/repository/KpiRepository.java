package com.cordillera.mskpi.repository;

import com.cordillera.mskpi.model.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * PATRÓN REPOSITORY - MS-KPI
 * ===========================
 * Abstrae el acceso a la base de datos.
 * La lógica de negocio (Service) no necesita conocer
 * los detalles de cómo se almacenan los KPIs.
 *
 * Si se cambia MySQL por otro motor de BD,
 * solo se modifica esta capa sin afectar el Service.
 */
@Repository
public interface KpiRepository extends JpaRepository<Kpi, Long> {

    // Buscar KPIs por tipo (VENTAS, RENTABILIDAD, etc.)
    List<Kpi> findByTipo(String tipo);

    // Buscar KPIs por área de negocio
    List<Kpi> findByArea(String area);

    // Buscar KPIs por estado (VERDE, AMARILLO, ROJO)
    List<Kpi> findByEstado(String estado);

    // Buscar KPIs entre fechas
    List<Kpi> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
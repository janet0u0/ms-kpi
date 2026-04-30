package com.cordillera.mskpi.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad KPI - MS-KPI
 * Representa un indicador clave de desempeño del Grupo Cordillera.
 * Permite medir ventas, rentabilidad, logística e inventario.
 *
 * Patrón aplicado: Repository Pattern
 * Esta entidad es gestionada por KpiRepository
 * que abstrae el acceso a la base de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "kpi")
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de KPI: VENTAS | RENTABILIDAD | INVENTARIO | LOGISTICA
    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate fecha;

    // Área de negocio: VENTAS | FINANZAS | OPERACIONES
    @Column(nullable = false)
    private String area;

    // Estado: VERDE | AMARILLO | ROJO
    @Column(nullable = false)
    private String estado;
}
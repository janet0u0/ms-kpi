package com.cordillera.mskpi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de salida para respuestas de KPI.
 * Expone todos los campos incluyendo el ID
 * generado por el sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiResponseDTO {

    private Long id;
    private String tipo;
    private BigDecimal valor;
    private LocalDate fecha;
    private String area;
    private String estado;
}
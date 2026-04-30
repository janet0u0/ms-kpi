package com.cordillera.mskpi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para creación/actualización de KPI.
 * Solo expone los campos que el cliente puede enviar.
 * El ID lo asigna el sistema automáticamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiRequestDTO {

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a 0")
    private BigDecimal valor;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El área es obligatoria")
    private String area;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
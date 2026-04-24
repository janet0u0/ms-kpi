package com.cordillera.mskpi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class KpiDTO {
    private Long id;
    private String tipo;
    private BigDecimal valor;
    private LocalDate fecha;
    private String area;
    private String estado;
}
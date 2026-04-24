package com.cordillera.mskpi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "kpi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String estado;
}
package com.cordillera.mskpi.controller;

import com.cordillera.mskpi.dto.KpiRequestDTO;
import com.cordillera.mskpi.dto.KpiResponseDTO;
import com.cordillera.mskpi.service.KpiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST - MS-KPI
 * Grupo Cordillera
 *
 * Gestiona los indicadores clave de desempeño (KPIs).
 * Recibe DTOs para separar la capa de presentación del modelo.
 * CORS configurado en CorsConfig.java (no en el controller).
 */
@RestController
@RequestMapping("/api/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService service;

    @GetMapping
    public ResponseEntity<List<KpiResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KpiResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<KpiResponseDTO>> listarPorTipo(
            @PathVariable String tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<KpiResponseDTO>> listarPorArea(
            @PathVariable String area) {
        return ResponseEntity.ok(service.listarPorArea(area));
    }

    // ✅ AGREGADO: endpoint que faltaba para findByEstado del repository
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<KpiResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<KpiResponseDTO> guardar(
            @Valid @RequestBody KpiRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KpiResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody KpiRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
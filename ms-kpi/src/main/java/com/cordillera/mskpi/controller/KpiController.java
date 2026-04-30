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
 */
@RestController
@RequestMapping("/api/kpis")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService service;

    /**
     * GET /api/kpis
     * Lista todos los KPIs
     */
    @GetMapping
    public ResponseEntity<List<KpiResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * GET /api/kpis/{id}
     * Busca un KPI por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<KpiResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    /**
     * GET /api/kpis/tipo/{tipo}
     * Busca KPIs por tipo (VENTAS, RENTABILIDAD, etc.)
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<KpiResponseDTO>> listarPorTipo(
            @PathVariable String tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    /**
     * GET /api/kpis/area/{area}
     * Busca KPIs por área de negocio
     */
    @GetMapping("/area/{area}")
    public ResponseEntity<List<KpiResponseDTO>> listarPorArea(
            @PathVariable String area) {
        return ResponseEntity.ok(service.listarPorArea(area));
    }

    /**
     * POST /api/kpis
     * Crea un nuevo KPI
     * @Valid activa las validaciones del DTO
     */
    @PostMapping
    public ResponseEntity<KpiResponseDTO> guardar(
            @Valid @RequestBody KpiRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    /**
     * PUT /api/kpis/{id}
     * Actualiza un KPI existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<KpiResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody KpiRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    /**
     * DELETE /api/kpis/{id}
     * Elimina un KPI por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
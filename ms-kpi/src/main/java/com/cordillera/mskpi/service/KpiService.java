package com.cordillera.mskpi.service;

import com.cordillera.mskpi.dto.KpiRequestDTO;
import com.cordillera.mskpi.dto.KpiResponseDTO;
import com.cordillera.mskpi.model.Kpi;
import com.cordillera.mskpi.repository.KpiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de Gestión de KPIs - MS-KPI
 * Contiene la lógica de negocio del microservicio.
 *
 * Patrón aplicado: Repository Pattern
 * Accede a datos exclusivamente a través de KpiRepository,
 * sin conocer los detalles de la base de datos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KpiService {

    private final KpiRepository repository;

    /**
     * Convierte entidad Kpi a KpiResponseDTO.
     * Separa la capa de persistencia de la presentación.
     */
    private KpiResponseDTO mapToDTO(Kpi kpi) {
        return new KpiResponseDTO(
                kpi.getId(),
                kpi.getTipo(),
                kpi.getValor(),
                kpi.getFecha(),
                kpi.getArea(),
                kpi.getEstado()
        );
    }

    /**
     * Lista todos los KPIs del sistema.
     */
    public List<KpiResponseDTO> listar() {
        log.info("Listando todos los KPIs");
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Busca un KPI por ID.
     * Lanza excepción si no existe.
     */
    public KpiResponseDTO buscar(Long id) {
        log.info("Buscando KPI con ID: {}", id);
        Kpi kpi = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "KPI no encontrado con ID: " + id));
        return mapToDTO(kpi);
    }

    /**
     * Crea un nuevo KPI.
     */
    public KpiResponseDTO guardar(KpiRequestDTO dto) {
        log.info("Creando nuevo KPI de tipo: {}", dto.getTipo());
        Kpi kpi = new Kpi();
        kpi.setTipo(dto.getTipo());
        kpi.setValor(dto.getValor());
        kpi.setFecha(dto.getFecha());
        kpi.setArea(dto.getArea());
        kpi.setEstado(dto.getEstado());
        return mapToDTO(repository.save(kpi));
    }

    /**
     * Actualiza un KPI existente.
     */
    public KpiResponseDTO actualizar(Long id, KpiRequestDTO dto) {
        log.info("Actualizando KPI con ID: {}", id);
        Kpi kpi = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "KPI no encontrado con ID: " + id));
        kpi.setTipo(dto.getTipo());
        kpi.setValor(dto.getValor());
        kpi.setFecha(dto.getFecha());
        kpi.setArea(dto.getArea());
        kpi.setEstado(dto.getEstado());
        return mapToDTO(repository.save(kpi));
    }

    /**
     * Elimina un KPI por ID.
     */
    public void eliminar(Long id) {
        log.info("Eliminando KPI con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("KPI no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Obtiene KPIs por tipo.
     */
    public List<KpiResponseDTO> listarPorTipo(String tipo) {
        log.info("Buscando KPIs de tipo: {}", tipo);
        return repository.findByTipo(tipo)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Obtiene KPIs por área.
     */
    public List<KpiResponseDTO> listarPorArea(String area) {
        log.info("Buscando KPIs del área: {}", area);
        return repository.findByArea(area)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
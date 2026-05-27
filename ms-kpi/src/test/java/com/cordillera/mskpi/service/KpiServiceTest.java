package com.cordillera.mskpi.service;

import com.cordillera.mskpi.dto.KpiRequestDTO;
import com.cordillera.mskpi.dto.KpiResponseDTO;
import com.cordillera.mskpi.exception.ResourceNotFoundException;
import com.cordillera.mskpi.model.Kpi;
import com.cordillera.mskpi.repository.KpiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KpiServiceTest {

    @Mock
    private KpiRepository repository;

    @InjectMocks
    private KpiService service;

    private Kpi kpi;
    private KpiRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        kpi = Kpi.builder()
                .id(1L)
                .tipo("VENTAS")
                .valor(new BigDecimal("150000.00"))
                .fecha(LocalDate.now())
                .area("VENTAS")
                .estado("VERDE")
                .build();

        requestDTO = new KpiRequestDTO(
                "VENTAS", new BigDecimal("150000.00"),
                LocalDate.now(), "VENTAS", "VERDE"
        );
    }

    // ── listar() ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe listar todos los KPIs")
    void listar_CuandoExistenKPIs_DeberiaRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(kpi));

        List<KpiResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("VENTAS", resultado.get(0).getTipo());
        assertEquals("VERDE", resultado.get(0).getEstado());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay KPIs")
    void listar_CuandoNoHayKPIs_DeberiaRetornarVacio() {
        when(repository.findAll()).thenReturn(List.of());

        List<KpiResponseDTO> resultado = service.listar();

        assertEquals(0, resultado.size());
    }

    // ── buscar() ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe buscar KPI por ID")
    void buscar_CuandoExiste_DeberiaRetornarKPI() {
        when(repository.findById(1L)).thenReturn(Optional.of(kpi));

        KpiResponseDTO resultado = service.buscar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("VENTAS", resultado.getTipo());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando KPI no existe")
    void buscar_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscar(99L));
        verify(repository).findById(99L);
    }

    // ── guardar() ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe guardar KPI usando Builder correctamente")
    void guardar_CuandoDatosValidos_DeberiaGuardar() {
        when(repository.save(any(Kpi.class))).thenReturn(kpi);

        KpiResponseDTO resultado = service.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("VENTAS", resultado.getTipo());
        assertEquals("VERDE", resultado.getEstado());
        verify(repository).save(any(Kpi.class));
    }

    // ── actualizar() ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe actualizar KPI existente")
    void actualizar_CuandoExiste_DeberiaActualizar() {
        KpiRequestDTO dtoNuevo = new KpiRequestDTO(
                "RENTABILIDAD", new BigDecimal("200000"),
                LocalDate.now(), "FINANZAS", "AMARILLO"
        );
        when(repository.findById(1L)).thenReturn(Optional.of(kpi));
        when(repository.save(any(Kpi.class))).thenReturn(kpi);

        KpiResponseDTO resultado = service.actualizar(1L, dtoNuevo);

        assertNotNull(resultado);
        verify(repository).save(any(Kpi.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar KPI inexistente")
    void actualizar_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.actualizar(99L, requestDTO));
        verify(repository, never()).save(any());
    }

    // ── eliminar() ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe eliminar KPI correctamente")
    void eliminar_CuandoExiste_DeberiaEliminar() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar KPI inexistente")
    void eliminar_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.eliminar(99L));
        verify(repository, never()).deleteById(any());
    }

    // ── listarPorTipo() ──────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por tipo")
    void listarPorTipo_CuandoExisten_DeberiaRetornarLista() {
        when(repository.findByTipo("VENTAS")).thenReturn(List.of(kpi));

        List<KpiResponseDTO> resultado = service.listarPorTipo("VENTAS");

        assertEquals(1, resultado.size());
        assertEquals("VENTAS", resultado.get(0).getTipo());
    }

    @Test
    @DisplayName("Debe retornar vacío si tipo no existe")
    void listarPorTipo_CuandoNoExiste_DeberiaRetornarVacio() {
        when(repository.findByTipo("LOGISTICA")).thenReturn(List.of());

        List<KpiResponseDTO> resultado = service.listarPorTipo("LOGISTICA");

        assertTrue(resultado.isEmpty());
    }

    // ── listarPorArea() ──────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por area")
    void listarPorArea_CuandoExisten_DeberiaRetornarLista() {
        when(repository.findByArea("VENTAS")).thenReturn(List.of(kpi));

        List<KpiResponseDTO> resultado = service.listarPorArea("VENTAS");

        assertEquals(1, resultado.size());
        assertEquals("VENTAS", resultado.get(0).getArea());
    }

    // ── listarPorEstado() ────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por estado VERDE")
    void listarPorEstado_CuandoVerde_DeberiaRetornarLista() {
        when(repository.findByEstado("VERDE")).thenReturn(List.of(kpi));

        List<KpiResponseDTO> resultado = service.listarPorEstado("VERDE");

        assertEquals(1, resultado.size());
        assertEquals("VERDE", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("Debe retornar vacío si estado no tiene KPIs")
    void listarPorEstado_CuandoNoExiste_DeberiaRetornarVacio() {
        when(repository.findByEstado("ROJO")).thenReturn(List.of());

        List<KpiResponseDTO> resultado = service.listarPorEstado("ROJO");

        assertTrue(resultado.isEmpty());
    }
}
package com.cordillera.mskpi.controller;

import com.cordillera.mskpi.dto.KpiRequestDTO;
import com.cordillera.mskpi.dto.KpiResponseDTO;
import com.cordillera.mskpi.exception.ResourceNotFoundException;
import com.cordillera.mskpi.service.KpiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KpiController.class)
@AutoConfigureMockMvc(addFilters = false)
class KpiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KpiService service;

    private KpiResponseDTO responseDTO;
    private KpiRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new KpiResponseDTO(
            1L, "VENTAS", new BigDecimal("150000.00"),
            LocalDate.now(), "VENTAS", "VERDE"
        );
        requestDTO = new KpiRequestDTO(
            "VENTAS", new BigDecimal("150000.00"),
            LocalDate.now(), "VENTAS", "VERDE"
        );
    }

    // ── GET /api/kpis ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar lista de KPIs cuando existen registros")
    void listar_CuandoExistenKPIs_DeberiaRetornarLista() throws Exception {
        when(service.listar()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/kpis"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tipo").value("VENTAS"))
            .andExpect(jsonPath("$[0].estado").value("VERDE"));
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay KPIs")
    void listar_CuandoNoHayKPIs_DeberiaRetornarVacio() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/kpis"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    // ── GET /api/kpis/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPI cuando existe el ID")
    void buscar_CuandoExiste_DeberiaRetornar200() throws Exception {
        when(service.buscar(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/kpis/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.tipo").value("VENTAS"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando KPI no existe")
    void buscar_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(service.buscar(99L))
            .thenThrow(new ResourceNotFoundException("KPI no encontrado con ID: 99"));

        mockMvc.perform(get("/api/kpis/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("KPI no encontrado con ID: 99"));
    }

    // ── GET /api/kpis/tipo/{tipo} ─────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por tipo")
    void listarPorTipo_DeberiaRetornarFiltrados() throws Exception {
        when(service.listarPorTipo("VENTAS")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/kpis/tipo/VENTAS"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tipo").value("VENTAS"));
    }

    @Test
    @DisplayName("Debe retornar vacío si tipo no existe")
    void listarPorTipo_CuandoNoExiste_DeberiaRetornarVacio() throws Exception {
        when(service.listarPorTipo("LOGISTICA")).thenReturn(List.of());

        mockMvc.perform(get("/api/kpis/tipo/LOGISTICA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    // ── GET /api/kpis/area/{area} ─────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por area")
    void listarPorArea_DeberiaRetornarFiltrados() throws Exception {
        when(service.listarPorArea("VENTAS")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/kpis/area/VENTAS"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].area").value("VENTAS"));
    }

    // ── GET /api/kpis/estado/{estado} ─────────────────────────────────────

    @Test
    @DisplayName("Debe retornar KPIs filtrados por estado VERDE")
    void listarPorEstado_DeberiaRetornarFiltrados() throws Exception {
        when(service.listarPorEstado("VERDE")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/kpis/estado/VERDE"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].estado").value("VERDE"));
    }

    // ── POST /api/kpis ────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe crear KPI y retornar 201")
    void guardar_CuandoDatosValidos_DeberiaRetornar201() throws Exception {
        when(service.guardar(any(KpiRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/kpis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.tipo").value("VENTAS"))
            .andExpect(jsonPath("$.estado").value("VERDE"));
    }

    @Test
    @DisplayName("Debe retornar 400 si tipo está vacío")
    void guardar_CuandoTipoVacio_DeberiaRetornar400() throws Exception {
        KpiRequestDTO invalido = new KpiRequestDTO(
            "", new BigDecimal("150000"), LocalDate.now(), "VENTAS", "VERDE"
        );

        mockMvc.perform(post("/api/kpis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe retornar 400 si valor es negativo")
    void guardar_CuandoValorNegativo_DeberiaRetornar400() throws Exception {
        KpiRequestDTO invalido = new KpiRequestDTO(
            "VENTAS", new BigDecimal("-100"), LocalDate.now(), "VENTAS", "VERDE"
        );

        mockMvc.perform(post("/api/kpis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalido)))
            .andExpect(status().isBadRequest());
    }

    // ── PUT /api/kpis/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("Debe actualizar KPI y retornar 200")
    void actualizar_CuandoExiste_DeberiaRetornar200() throws Exception {
        when(service.actualizar(eq(1L), any(KpiRequestDTO.class)))
            .thenReturn(responseDTO);

        mockMvc.perform(put("/api/kpis/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debe retornar 404 si KPI a actualizar no existe")
    void actualizar_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(service.actualizar(eq(99L), any(KpiRequestDTO.class)))
            .thenThrow(new ResourceNotFoundException("KPI no encontrado con ID: 99"));

        mockMvc.perform(put("/api/kpis/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isNotFound());
    }

    // ── DELETE /api/kpis/{id} ─────────────────────────────────────────────

    @Test
    @DisplayName("Debe eliminar KPI y retornar 204")
    void eliminar_CuandoExiste_DeberiaRetornar204() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/kpis/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 404 si KPI a eliminar no existe")
    void eliminar_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        doThrow(new ResourceNotFoundException("KPI no encontrado con ID: 99"))
            .when(service).eliminar(99L);

        mockMvc.perform(delete("/api/kpis/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("KPI no encontrado con ID: 99"));
    }

    // ── Error general ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Debe retornar 500 ante error interno no controlado")
    void error_CuandoExcepcionGenerica_DeberiaRetornar500() throws Exception {
        when(service.listar())
            .thenThrow(new RuntimeException("Error inesperado"));

        mockMvc.perform(get("/api/kpis"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("Error interno del servidor"));
    }
}
package com.cordillera.mskpi.repository;

import com.cordillera.mskpi.model.Kpi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class KpiRepositoryTest {

    @Autowired
    private KpiRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        repository.save(Kpi.builder()
                .tipo("VENTAS").valor(new BigDecimal("150000"))
                .fecha(LocalDate.now()).area("VENTAS").estado("VERDE").build());

        repository.save(Kpi.builder()
                .tipo("RENTABILIDAD").valor(new BigDecimal("80000"))
                .fecha(LocalDate.now()).area("FINANZAS").estado("AMARILLO").build());

        repository.save(Kpi.builder()
                .tipo("INVENTARIO").valor(new BigDecimal("30000"))
                .fecha(LocalDate.now()).area("OPERACIONES").estado("ROJO").build());
    }

    @Test
    @DisplayName("Debe guardar KPI correctamente")
    void guardar_CuandoKpiValido_DeberiaPersistir() {
        Kpi nuevo = Kpi.builder()
                .tipo("LOGISTICA").valor(new BigDecimal("50000"))
                .fecha(LocalDate.now()).area("OPERACIONES").estado("VERDE").build();

        Kpi resultado = repository.save(nuevo);

        assertNotNull(resultado.getId());
        assertEquals("LOGISTICA", resultado.getTipo());
    }

    @Test
    @DisplayName("Debe encontrar KPIs por tipo VENTAS")
    void findByTipo_CuandoExiste_DeberiaRetornarLista() {
        List<Kpi> resultado = repository.findByTipo("VENTAS");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("VENTAS", resultado.get(0).getTipo());
    }

    @Test
    @DisplayName("Debe retornar vacío si tipo no existe")
    void findByTipo_CuandoNoExiste_DeberiaRetornarVacio() {
        List<Kpi> resultado = repository.findByTipo("TIPO_INEXISTENTE");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe encontrar KPIs por area")
    void findByArea_CuandoExiste_DeberiaRetornarLista() {
        List<Kpi> resultado = repository.findByArea("VENTAS");

        assertFalse(resultado.isEmpty());
        assertEquals("VENTAS", resultado.get(0).getArea());
    }

    @Test
    @DisplayName("Debe encontrar KPIs por estado VERDE")
    void findByEstado_CuandoVerde_DeberiaRetornarLista() {
        List<Kpi> resultado = repository.findByEstado("VERDE");

        assertFalse(resultado.isEmpty());
        resultado.forEach(k -> assertEquals("VERDE", k.getEstado()));
    }

    @Test
    @DisplayName("Debe encontrar KPIs entre fechas")
    void findByFechaBetween_DeberiaRetornarKPIs() {
        LocalDate inicio = LocalDate.now().minusDays(1);
        LocalDate fin = LocalDate.now().plusDays(1);

        List<Kpi> resultado = repository.findByFechaBetween(inicio, fin);

        assertFalse(resultado.isEmpty());
        assertEquals(3, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar todos los KPIs")
    void findAll_DeberiaRetornarTodos() {
        assertEquals(3, repository.findAll().size());
    }

    @Test
    @DisplayName("Debe eliminar KPI por ID")
    void deleteById_CuandoExiste_DeberiaEliminar() {
        Kpi kpi = repository.findByTipo("VENTAS").get(0);
        Long id = kpi.getId();

        repository.deleteById(id);

        Optional<Kpi> eliminado = repository.findById(id);
        assertTrue(eliminado.isEmpty());
    }
}
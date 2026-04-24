package com.cordillera.mskpi.controller;

import com.cordillera.mskpi.model.Kpi;
import com.cordillera.mskpi.service.KpiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kpi")
@CrossOrigin("*")
public class KpiController {

    private final KpiService service;

    public KpiController(KpiService service) {
        this.service = service;
    }

    @GetMapping
    public List<Kpi> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Kpi buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Kpi guardar(@RequestBody Kpi kpi) {
        return service.guardar(kpi);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
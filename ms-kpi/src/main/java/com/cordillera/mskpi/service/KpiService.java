package com.cordillera.mskpi.service;

import com.cordillera.mskpi.model.Kpi;
import com.cordillera.mskpi.repository.KpiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpiService {

    private final KpiRepository repository;

    public KpiService(KpiRepository repository) {
        this.repository = repository;
    }

    public List<Kpi> listar() {
        return repository.findAll();
    }

    public Kpi buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Kpi guardar(Kpi kpi) {
        return repository.save(kpi);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
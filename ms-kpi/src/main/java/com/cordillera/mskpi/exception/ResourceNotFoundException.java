package com.cordillera.mskpi.exception;

import java.io.Serial;

/**
 * Se lanza cuando no se encuentra un KPI en la base de datos.
 * Buena práctica: usar excepciones específicas para un manejo de errores más claro.
 */
public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
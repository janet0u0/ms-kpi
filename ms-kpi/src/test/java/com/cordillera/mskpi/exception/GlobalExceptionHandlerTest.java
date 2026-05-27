package com.cordillera.mskpi.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void handleBadRequestTest() {

        IllegalArgumentException ex =
                new IllegalArgumentException("Dato invalido");

        ResponseEntity<Map<String, String>> response =
                handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST,
                response.getStatusCode());

        Map<String, String> body = response.getBody();

        assertNotNull(body);

        assertEquals("Dato invalido",
                body.get("error"));
    }
}
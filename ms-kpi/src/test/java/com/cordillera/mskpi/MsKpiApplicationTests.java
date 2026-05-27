package com.cordillera.mskpi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class MsKpiApplicationTests {

    @Test
    @DisplayName("El metodo main debe ejecutarse sin errores")
    void main_DeberiaEjecutarSinErrores() {
        try (MockedStatic<SpringApplication> mocked =
                Mockito.mockStatic(SpringApplication.class)) {

            mocked.when(() -> SpringApplication.run(
                    MsKpiApplication.class, new String[]{}))
                    .thenReturn(null);

            MsKpiApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(
                    MsKpiApplication.class, new String[]{}));
        }
    }
}
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void load_shouldReadYamlAndMapToObject() {
        AppConfig config = AppConfig.load("test-config.yaml");

        assertNotNull(config);
        assertNotNull(config.getStorage());
        assertEquals("logs/app.log", config.getStorage().getLogfile());
    }

    @Test
    void load_missingFile_shouldThrowRuntimeException() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            AppConfig.load("nonexistent.yaml");
        });
        System.out.println(ex.getMessage());
        assertTrue(ex.getMessage().contains("Failed to load configuration"));
    }
}

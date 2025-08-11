package org.example.util;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

public class AppConfig {
    private StorageConfig storage;

    public StorageConfig getStorage() { return storage; }
    public void setStorage(StorageConfig storage) { this.storage = storage; }

    public static class StorageConfig {
        private String logfile;

        public String getLogfile() { return logfile; }
        public void setLogfile(String logfile) { this.logfile = logfile; }
    }

    public static AppConfig load(String resourceName) {
        Yaml yaml = new Yaml();
        try (InputStream in = AppConfig.class.getClassLoader()
                .getResourceAsStream(resourceName)) {
            if (in == null) {
                throw new RuntimeException(resourceName + " not found in classpath");
            }
            return yaml.loadAs(in, AppConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration from " + resourceName, e);
        }
    }
}
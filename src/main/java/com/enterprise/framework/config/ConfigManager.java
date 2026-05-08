package com.enterprise.framework.config;

import com.enterprise.framework.constants.FrameworkConstants;
import com.enterprise.framework.enums.EnvironmentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigManager - Singleton pattern for environment-aware config loading.
 *
 * INTERN NOTE: A Singleton ensures only ONE Properties object is created
 * and shared across all threads (read-only after init = thread-safe).
 *
 * How it works:
 *   1. Reads -Denv=qa from Maven/JVM system property
 *   2. Loads src/main/resources/environments/qa.properties
 *   3. Any class calls: ConfigManager.getInstance().get("app.url")
 *
 * SOLID: Single Responsibility (config loading only).
 *        Open/Closed: add new envs by adding .properties files, no code change.
 */
public final class ConfigManager {

    private static final Logger log = LogManager.getLogger(ConfigManager.class);
    private static volatile ConfigManager instance;
    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    /** Thread-safe double-checked locking singleton. */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadConfig() {
        String envValue = System.getProperty("env", "qa");
        EnvironmentType env = EnvironmentType.fromString(envValue);
        String configPath = FrameworkConstants.CONFIG_BASE_PATH + env.name().toLowerCase() + ".properties";
        log.info("Loading config [{}] from: {}", env, configPath);
        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
            log.info("Config loaded. Properties count: {}", properties.size());
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config: " + configPath +
                ". Ensure -Denv is set correctly.", e);
        }
    }

    /**
     * Returns a property value. System property overrides file (useful for CI).
     * @throws RuntimeException if key is missing
     */
    public String get(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) return sysProp.trim();
        String value = properties.getProperty(key);
        if (value == null || value.isBlank())
            throw new RuntimeException("Property [" + key + "] not found in config.");
        return value.trim();
    }

    /** Returns a property as int. */
    public int getInt(String key) { return Integer.parseInt(get(key)); }

    /** Returns a property as boolean. */
    public boolean getBoolean(String key) { return Boolean.parseBoolean(get(key)); }

    /** Shortcut: returns the application base URL. */
    public String getAppUrl() { return get("app.url"); }

    /** Returns the default browser from config (overridable by -Dbrowser=). */
    public String getDefaultBrowser() { return get("browser.default"); }
}

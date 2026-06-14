package com.netsymptom.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(
                "src/test/resources/config/config.properties")) {
            properties.load(fis);
            log.info("Configuration loaded successfully.");
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in config.properties", key);
        }
        return value;
    }

    public static String getBaseUrl()           { return get("base.url"); }
    public static String getBrowser()           { return get("browser"); }
    public static boolean isHeadless()          { return Boolean.parseBoolean(get("headless")); }
    public static int getImplicitWait()         { return Integer.parseInt(get("implicit.wait")); }
    public static int getExplicitWait()         { return Integer.parseInt(get("explicit.wait")); }
    public static int getDiagnosticWait()       { return Integer.parseInt(get("diagnostic.wait")); }
    public static int getPageLoadTimeout()      { return Integer.parseInt(get("page.load.timeout")); }
    public static boolean isScreenshotOnFail()  { return Boolean.parseBoolean(get("screenshot.on.failure")); }
    public static String getScreenshotDir()     { return get("screenshot.dir"); }
    public static int getWindowWidth()          { return Integer.parseInt(get("window.width")); }
    public static int getWindowHeight()         { return Integer.parseInt(get("window.height")); }
}

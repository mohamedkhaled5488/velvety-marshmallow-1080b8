package com.netsymptom.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver() {
        if (driverThreadLocal.get() != null) return;

        String browser = ConfigReader.getBrowser().toLowerCase().trim();
        boolean headless = ConfigReader.isHeadless();
        WebDriver driver;

        log.info("Initializing '{}' browser (headless={})", browser, headless);

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOpts = new FirefoxOptions();
                if (headless) ffOpts.addArguments("--headless");
                driver = new FirefoxDriver(ffOpts);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOpts = new EdgeOptions();
                if (headless) edgeOpts.addArguments("--headless");
                driver = new EdgeDriver(edgeOpts);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOpts = new ChromeOptions();
                if (headless) {
                    chromeOpts.addArguments("--headless=new");
                }
                chromeOpts.addArguments(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--disable-extensions",
                    "--disable-infobars",
                    "--remote-allow-origins=*"
                );
                driver = new ChromeDriver(chromeOpts);
                break;
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        driver.manage().window().setSize(
                new Dimension(ConfigReader.getWindowWidth(), ConfigReader.getWindowHeight()));

        driverThreadLocal.set(driver);
        log.info("Driver initialized and window sized to {}x{}",
                ConfigReader.getWindowWidth(), ConfigReader.getWindowHeight());
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            log.info("Driver quit and removed from ThreadLocal.");
        }
    }
}

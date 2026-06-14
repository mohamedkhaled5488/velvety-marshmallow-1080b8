package com.netsymptom.hooks;

import com.netsymptom.utils.ConfigReader;
import com.netsymptom.utils.DriverManager;
import com.netsymptom.utils.WaitUtils;
import com.netsymptom.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before(order = 0)
    public void initDriver(Scenario scenario) {
        DriverManager.initDriver();
        log.info("▶ Scenario: {} | Browser: {}", scenario.getName(), ConfigReader.getBrowser());
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ScreenshotUtils.captureAsBytes();
            if (screenshot != null) {
                scenario.attach(screenshot, "image/png", "Failure screenshot");
            }
            log.error("✗ FAILED: {}", scenario.getName());
        } else {
            log.info("✓ PASSED: {}", scenario.getName());
        }
        DriverManager.quitDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ScreenshotUtils.captureAsBytes();
            if (screenshot != null) {
                scenario.attach(screenshot, "image/png", "Step failure");
            }
        }
    }
}

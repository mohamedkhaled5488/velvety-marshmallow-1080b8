package com.netsymptom.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and saves it to the configured directory.
     * @param scenarioName Name used in the file name.
     * @return Absolute path of the saved screenshot, or null on failure.
     */
    public static String capture(String scenarioName) {
        if (!ConfigReader.isScreenshotOnFail()) return null;

        try {
            File source = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String path = ConfigReader.getScreenshotDir() + "/" + safeName + "_" + timestamp + ".png";
            File dest = new File(path);
            FileUtils.copyFile(source, dest);
            log.info("Screenshot saved: {}", path);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    /** Capture as byte array — used to embed in Extent Reports. */
    public static byte[] captureAsBytes() {
        try {
            return ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot bytes: {}", e.getMessage());
            return new byte[0];
        }
    }
}

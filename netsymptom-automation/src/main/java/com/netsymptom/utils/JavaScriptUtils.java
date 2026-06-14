package com.netsymptom.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private static final Logger log = LogManager.getLogger(JavaScriptUtils.class);

    private JavaScriptUtils() {}

    private static JavascriptExecutor js() {
        return (JavascriptExecutor) DriverManager.getDriver();
    }

    /** Click via JS — bypasses overlapping elements. */
    public static void click(WebElement element) {
        log.debug("JS click on element: {}", element.getTagName());
        js().executeScript("arguments[0].click();", element);
    }

    /** Set value on a number/text input and dispatch events so JS listeners fire. */
    public static void setValue(WebElement input, String value) {
        js().executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
            input, value);
    }

    /** Set slider value and trigger the oninput handler. */
    public static void setSliderValue(WebElement slider, int value) {
        js().executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
            slider, value);
    }

    /** Read computed CSS property. */
    public static String getCssValue(WebElement element, String property) {
        return (String) js().executeScript(
            "return window.getComputedStyle(arguments[0]).getPropertyValue(arguments[1]);",
            element, property);
    }

    /** Scroll to top of page. */
    public static void scrollToTop() {
        js().executeScript("window.scrollTo(0, 0);");
    }

    /** Scroll to bottom. */
    public static void scrollToBottom() {
        js().executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /** Return the inner text of an element by ID. */
    public static String getTextById(String id) {
        return (String) js().executeScript(
            "var el = document.getElementById(arguments[0]); return el ? el.innerText : '';", id);
    }

    /** Check if element with id has class. */
    public static boolean hasClass(String id, String className) {
        return (Boolean) js().executeScript(
            "var el = document.getElementById(arguments[0]);" +
            "return el ? el.classList.contains(arguments[1]) : false;",
            id, className);
    }

    /** Select a dropdown option by value using JS. */
    public static void selectByValue(WebElement select, String value) {
        js().executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
            select, value);
    }
}

package com.netsymptom.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features  = "src/test/resources/features/smoke",
    glue      = {"com.netsymptom.stepdefs", "com.netsymptom.hooks"},
    tags      = "@smoke",
    plugin    = {
        "pretty",
        "html:target/cucumber-reports/smoke/index.html",
        "json:target/cucumber-reports/smoke/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

package com.netsymptom.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features  = "src/test/resources/features/regression",
    glue      = {"com.netsymptom.stepdefs", "com.netsymptom.hooks"},
    tags      = "@regression",
    plugin    = {
        "pretty",
        "html:target/cucumber-reports/regression/index.html",
        "json:target/cucumber-reports/regression/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

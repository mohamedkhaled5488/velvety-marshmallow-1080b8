package com.netsymptom.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features  = {"src/test/resources/features/functional",
                 "src/test/resources/features/accessibility"},
    glue      = {"com.netsymptom.stepdefs", "com.netsymptom.hooks"},
    tags      = "@functional or @accessibility",
    plugin    = {
        "pretty",
        "html:target/cucumber-reports/functional/index.html",
        "json:target/cucumber-reports/functional/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class FunctionalTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

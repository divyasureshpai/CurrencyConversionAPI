package com.api.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/Features"},glue = "com.api.stepDefinitions", plugin = {"pretty","html:test-output/report.html"})
public class TestRunner {
}

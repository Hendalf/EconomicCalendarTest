package com;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "com/teststepdefs",
        tags = "@CalendarTest",
        plugin = {"json:target/cucumber-report/cucumber.json"},
        dryRun = false,
        strict = false
)
public class TestRunner {
}
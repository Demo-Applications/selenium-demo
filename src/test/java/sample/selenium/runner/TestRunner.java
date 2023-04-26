package sample.selenium.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = "sample.selenium.stepdefinitions", plugin = {
		"pretty", "html:target/cucumber-html-report.html" } )
public class TestRunner extends AbstractTestNGCucumberTests {

}

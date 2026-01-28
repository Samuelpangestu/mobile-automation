package com.indodax.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.indodax.mobile.tests")
@ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
public class TestRunner {
    // This class is used to run Cucumber tests with JUnit Platform
    // Tag filtering is controlled via -Dcucumber.filter.tags system property
    // Example: mvn test -Dcucumber.filter.tags="@test"
    // If no tag is specified, all @mobile tests will run
}

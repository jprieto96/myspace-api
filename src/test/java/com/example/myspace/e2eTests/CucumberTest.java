package com.example.myspace.e2eTests;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("e2eTests")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.myspace.e2eTests.steps")
public class CucumberTest {

}

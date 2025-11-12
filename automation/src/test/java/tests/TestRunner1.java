package tests;

import general.GeneralFunctions;
import general.WebDriverFactory;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features/01_login.feature" },
        glue = {"stepdefs"},
        plugin = { "pretty", "html:target/cucumber1" }
)

public  class TestRunner1 {

    static Date startTime = null;
    static Date endTime = null;

    public static ArrayList<String> automationSteps;

    public static ArrayList<String> expectedResults;
    @BeforeClass
    public static void  beforeClass() throws SQLException {

        startTime = GeneralFunctions.getTime();
        automationSteps = new ArrayList<String>();
        expectedResults=new ArrayList<String>();
    }
    @AfterClass
    public static void AfterClass(){
        try {
            WebDriverFactory.finishDriver();
            endTime = GeneralFunctions.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

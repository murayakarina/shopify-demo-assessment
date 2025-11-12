package stepdefs;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.ConfigProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import general.GeneralFunctions;
import general.MainCall;
import general.Screenshots;
import general.TestRail;
import io.cucumber.java.Status;
import tests.TestSuite;

public class Hooks extends TestSuite {

    static ExtentTest logger;

    @Before
    public static void testStart(Scenario scenario) throws Throwable {
        beforeAddingStepsLength = automationSteps.size();
        beforeAddingExpectedResultLength = expectedResults.size();
        MainCall.caseID = "";
        if (ConfigProperties.isReportingEnable.equals("true")) {
            logger = MainCall.getExtentReport().startTest(scenario.getName(), "");
            logger.setStartedTime(GeneralFunctions.getTime());
        }
    }

    @After
    public static void testEnd(Scenario scenario) throws Throwable {
        afterAddingStepsLength = automationSteps.size();
        afterAddingExpectedResultLength = expectedResults.size();
        Status status = scenario.getStatus();
        if (status.equals(Status.FAILED)) {
            String screenShot = Screenshots.takeScreenshot(scenario.getName());
            screenShotCollection.add(Screenshots.screenShot);
            TestSuite.setFailCount(TestSuite.getFailCount() + 1);
            if (ConfigProperties.isReportingEnable.equals("true")) {
                logger.log(LogStatus.FAIL, logger.addScreenCapture(screenShot));
                logger.log(LogStatus.FAIL, "Test Case Failed reason is: " + status.getClass().equals(Status.FAILED));
            }
        } else if (status.equals(Status.SKIPPED)) {
            TestSuite.setSkippedCount(TestSuite.getSkippedCount() + 1);
            if (ConfigProperties.isReportingEnable.equals("true")) {
                logger.log(LogStatus.SKIP, "Test Case Skipped is: ");
            }
        } else {
            TestSuite.setPassCount((TestSuite.getPassCount() + 1));
            if (ConfigProperties.isReportingEnable.equals("true")) {
                logger.log(LogStatus.PASS, scenario.getName() + " is Passed");
            }
        }
        if (ConfigProperties.isReportingEnable.equals("true")) {
            logger.setEndedTime(GeneralFunctions.getTime());
            MainCall.getExtentReport().endTest(logger);
        }
        TestRail.getCaseIdandResultBDD(scenario, MainCall.caseID, beforeAddingStepsLength, afterAddingStepsLength, automationSteps, beforeAddingExpectedResultLength, afterAddingExpectedResultLength, expectedResults, null);
    }
}
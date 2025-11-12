package general;

import config.ConfigProperties;
import io.cucumber.java.Scenario;

import net.minidev.json.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class TestRail {

    public static String TestRunName;
    public static Long suiteId;
    static JSONParser jsonParser;
    static APIClient client;

    public static void getCaseIdandResultBDD(Scenario scenario, String CaseId, Integer beforeAddingStepsLength, Integer afterAddingStepsLength, ArrayList<String> automationSteps, Integer beforeAddingExpectedResultLength, Integer afterAddingExpectedResultLength, ArrayList<String> expectedResults, String customPreconds) throws IOException, IllegalAccessException {
        if (CaseId != null && CaseId != "") {
            DataList.caseid.add(CaseId);
        }

        DataList.result.add(getCaseResultBdd(scenario));
        DataList.updateData.add(updatetestcaseBdd(scenario, beforeAddingStepsLength, afterAddingStepsLength, automationSteps, beforeAddingExpectedResultLength, afterAddingExpectedResultLength, expectedResults, customPreconds));
    }
    static {
        TestRunName = "Zebra UI"+ "-Automation-Test-" + LocalDate.now();
        jsonParser = new JSONParser();
        if (!Stream.of(ConfigProperties.testRailUrl, ConfigProperties.testRailUsername, ConfigProperties.testRailPassword).anyMatch(Objects::isNull) && !Stream.of(ConfigProperties.testRailUrl, ConfigProperties.testRailUsername, ConfigProperties.testRailPassword).anyMatch((i) -> {
            return i.isEmpty();
        })) {
            try {
                client = new APIClient(ConfigProperties
                        .testRailUrl);
                client.setUser(ConfigProperties.testRailUsername);
                client.setPassword(ConfigProperties.testRailPassword);
            } catch (Exception var1) {
                System.out.println("Error interacting with TestRail API: " + var1.getMessage());
            }
        } else {
            System.out.println("Failed to read credentials for Test Rail. Please make sure the credentials are present in either config.properties or environmental variables");
        }

    }


    public static Map<String, Object> getCaseResultBdd(Scenario scenario) throws IllegalAccessException {
        Map<String, Object> data = new HashMap<>();

        if (scenario.isFailed()) {
            data.put("status_id", 5); // Failed status
            data.put("comment", scenario.getStatus().toString());
        } else {
            data.put("status_id", 1); // Passed status
        }

        return data;
    }

    public static Map updatetestcaseBdd(Scenario scenario, Integer beforeAddingStepsLength, Integer afterAddingStepsLength, ArrayList<String> automationSteps, Integer beforeAddingExpectedResultLength, Integer afterAddingExpectedResultLength, ArrayList<String> expectedResults, String customPreconds) {
        ArrayList<HashMap<String, String>> dataset = new ArrayList();
        Map data1 = new HashMap();

        for(int i = beforeAddingExpectedResultLength; i < afterAddingExpectedResultLength; ++i) {
            HashMap<String, String> stepData = new HashMap();
            if (automationSteps.get(i) == null) {
                stepData.put("content", "");
            } else {
                stepData.put("content", (String)automationSteps.get(i));
            }

            stepData.put("expected", (String)expectedResults.get(i));
            dataset.add(stepData);
        }

        System.out.println("\n \n" + dataset);
        data1.put("custom_steps_separated", dataset);
        data1.put("custom_description", "" + scenario.getName());
        data1.put("custom_preconds", "" + customPreconds);
        return data1;
    }


    public static void createSuite() throws IOException {
        String uniqueRunName = Long.toString(System.currentTimeMillis());

        Map data = new HashMap();
        data.put("include_all", false);
        System.out.println(DataList.caseid);
        data.put("case_ids", DataList.caseid);
        data.put("name", TestRunName + " - " + uniqueRunName);

        System.out.println("Request Payload: " + data);

        JSONObject c = null;
        if (client != null) {
            try {
                // Check if the suite already exists
                Object response = client.sendGets("get_runs/" + ConfigProperties.testTrailProjectId);

                System.out.println("Response from get_runs: " + response);

                if (response instanceof JSONObject) {
                    JSONObject responseObj = (JSONObject) response;
                    JSONArray existingSuites = (JSONArray) responseObj.get("runs");

                    Optional<JSONObject> existingSuite = existingSuites.stream()
                            .filter(suite -> ((JSONObject) suite).get("name").equals(TestRunName))
                            .findFirst();

                    if (existingSuite.isPresent()) {
                        // Suite already exists, retrieve its ID
                        suiteId = (Long) existingSuite.get().get("id");
                    } else {
                        // Create a new suite
                        c = (JSONObject) client.sendPost("add_run/" + ConfigProperties.testTrailProjectId, data);
                        suiteId = (Long) c.get("id");
                    }
                } else {
                    System.out.println("Unexpected response format for get_runs");
                }
            } catch (Exception var3) {
                System.out.println("Error interacting with TestRail API: " + var3.getMessage());
            }
        }
    }
    public static void updateTestRail() throws IOException, APIException {
        if (client != null && suiteId != null) {
            System.out.println(DataList.caseid);

            for(int i = 0; i < DataList.caseid.size(); ++i) {
                System.out.println(DataList.caseid.get(i) + "" + DataList.result.get(i));
                try {
                    client.sendPost("add_result_for_case/" + suiteId + "/" + DataList.caseid.get(i), DataList.result.get(i));
                } catch (Exception var2) {
                    System.out.println("Error interacting with TestRail API: " + var2.getMessage());
                }
            }
        }

    }
    public static void AttachImagesWithTestResults(ArrayList<File> screenShotCollection) throws IOException, APIException {
        int counter = 0;
        client = new APIClient(ConfigProperties.testRailUrl);
        client.setUser(ConfigProperties.testRailUsername);
        client.setPassword(ConfigProperties.testRailPassword);

        if (suiteId != null) {

            for(int i = 0; i < DataList.caseid.size(); ++i) {
                JSONObject k = null;
                k = (JSONObject)client.sendGets("get_results_for_case/" + suiteId + "/" + DataList.caseid.get(i));
                System.out.println(k.toString());
                JSONArray results = (JSONArray)k.get("results");
                System.out.println(results.toString());
                JSONObject jsonBody = (JSONObject)results.get(0);
                Long statusResult = (Long)jsonBody.get("status_id");
                if (statusResult == 5L) {
                    String getFileName = String.valueOf(screenShotCollection.get(counter));
                    Long resultId = (Long)jsonBody.get("id");
                    JSONObject var10000 = (JSONObject)client.sendPost("add_attachment_to_result/" + resultId, getFileName);
                    ++counter;
                }
            }
        }

    }

}

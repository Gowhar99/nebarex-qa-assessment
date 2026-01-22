

package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;
import utils.ExtentTestListener;
import com.aventstack.extentreports.ExtentTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class TrackingInfoApiTest {

    @Test
    public void verifyTrackingInfoApi() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        // Call API
        Response response = ApiClient.getTrackingInfo();

        // Status code
        int statusCode = response.getStatusCode();
        extentTest.info("API Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        // Response time
        long responseTime = response.getTime();
        extentTest.info("API Response Time: " + responseTime + " ms");
        Assert.assertTrue(responseTime < 3000);

        // Content type
        String contentType = response.getHeader("Content-Type");
        Assert.assertTrue(contentType.contains("application/json"));

        // Pretty JSON
        String prettyJson = response.asPrettyString();

        // ===== PRINT TO TERMINAL =====
        System.out.println("===== API RESPONSE (PRETTY JSON) =====");
        System.out.println(prettyJson);

        // ===== SAVE TO FILE =====
        try {
            String filePath = "target/api-response.json";
            Files.write(Paths.get(filePath), prettyJson.getBytes());
            extentTest.info("API response saved to file: " + filePath);
        } catch (Exception e) {
            extentTest.warning("Failed to save API response: " + e.getMessage());
        }

        // Parse response
        List<Map<String, Object>> apiList = response.jsonPath().getList("$");

        // Print count + records
        System.out.println("===== RECORD COUNT =====");
        System.out.println("Total records: " + apiList.size());

        System.out.println("===== ALL RECORDS =====");
        for (Map<String, Object> record : apiList) {
            System.out.println(record);
        }

        Assert.assertFalse(apiList.isEmpty());
        Assert.assertEquals(apiList.size(), 30);

        // Validate core business fields
        for (Map<String, Object> item : apiList) {

            Assert.assertTrue(
                    item.containsKey("name") || item.containsKey("location"),
                    "Record must contain name or location"
            );

            Assert.assertTrue(item.containsKey("lat"), "Missing latitude");
            Assert.assertTrue(item.containsKey("lng"), "Missing longitude");

            double lat = Double.parseDouble(item.get("lat").toString());
            double lng = Double.parseDouble(item.get("lng").toString());

            Assert.assertTrue(lat >= -90 && lat <= 90);
            Assert.assertTrue(lng >= -180 && lng <= 180);
        }

        // Attach full response to Extent
        extentTest.info(
                "<details><summary>Full API Response</summary><pre>" +
                        prettyJson +
                        "</pre></details>"
        );

        extentTest.pass("Tracking Info API validated successfully");
    }

    // Negative: missing API key
    @Test
    public void verifyApiFailsWithoutApiKey() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        Response response = RestAssured
                .given()
                .get("https://dev.nebarex.com/file/tracking/tracking-info");

        int statusCode = response.getStatusCode();
        extentTest.info("Status without API key: " + statusCode);

        Assert.assertTrue(statusCode == 401 || statusCode == 403);
        extentTest.pass("API correctly rejects missing API key");
    }

    // Negative: POST method behavior (informational)
    @Test
    public void verifyApiPostMethodBehavior() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        Response response = RestAssured
                .given()
                .header("x-api-key", "A9fK3P2Qx7MZ8JrC5LwH6BvTnE4DYSd")
                .post("https://dev.nebarex.com/file/tracking/tracking-info");

        int statusCode = response.getStatusCode();
        extentTest.info("POST method status code: " + statusCode);

        Assert.assertTrue(statusCode >= 200 && statusCode < 500);
        extentTest.pass("POST method behavior logged");
    }
}

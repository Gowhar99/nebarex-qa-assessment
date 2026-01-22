
// package api;

// import io.restassured.RestAssured;
// import io.restassured.response.Response;
// import org.testng.Assert;
// import org.testng.annotations.Test;
// import utils.ApiClient;

// import java.util.List;
// import java.util.Map;

// public class TrackingInfoApiTest {

//     @Test
//     public void verifyTrackingInfoApi() {

//         Response response = ApiClient.getTrackingInfo();

//         //  Status code
//         Assert.assertEquals(response.getStatusCode(), 200);

//         //  Response time
//         long responseTime = response.getTime();
//         System.out.println("API Response Time: " + responseTime + " ms");
//         Assert.assertTrue(responseTime < 3000);

//         //  Header validation
//         String contentType = response.getHeader("Content-Type");
//         Assert.assertTrue(contentType.contains("application/json"));

//         // Body not empty
//         Assert.assertNotNull(response.getBody());
//         Assert.assertTrue(response.asString().length() > 2);

//         // Validate list structure
//         List<Map<String, Object>> apiList =
//                 response.jsonPath().getList("$");

//         Assert.assertNotNull(apiList);
//         Assert.assertFalse(apiList.isEmpty());

//         //  Validate object fields exist
//         Map<String, Object> firstItem = apiList.get(0);
//         Assert.assertTrue(firstItem.size() > 0);

//         // Print response for visibility
//         System.out.println("API SAMPLE RESPONSE:");
//         System.out.println(response.asPrettyString());
//     }
    

//     //Negative Test case for API key validation
//     @Test
//     public void verifyApiFailsWithoutApiKey() {

//         Response response = RestAssured
//                 .given()
//                 .get("https://dev.nebarex.com/file/tracking/tracking-info");

//         Assert.assertTrue(
//             response.getStatusCode() == 401
//             || response.getStatusCode() == 403
//         );
//     }
// }
package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;
import utils.ExtentTestListener;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;
import java.util.Map;

public class TrackingInfoApiTest {

    @Test
    public void verifyTrackingInfoApi() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        Response response = ApiClient.getTrackingInfo();

        //  Status code
        int statusCode = response.getStatusCode();
        extentTest.info("API Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        //  Response time
        long responseTime = response.getTime();
        extentTest.info("API Response Time: " + responseTime + " ms");
        Assert.assertTrue(responseTime < 3000, "Response time should be under 3 seconds");

        // Header validation
        String contentType = response.getHeader("Content-Type");
        extentTest.info("Content-Type Header: " + contentType);
        Assert.assertTrue(contentType.contains("application/json"));

        //  Body not empty
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.asString().length() > 2);

        // Validate list structure
        List<Map<String, Object>> apiList = response.jsonPath().getList("$");
        extentTest.info("Total records returned by API: " + apiList.size());

        Assert.assertNotNull(apiList);
        Assert.assertFalse(apiList.isEmpty());

        // Validate expected record count
        Assert.assertEquals(apiList.size(), 30, "API should return exactly 30 records");

        //  Log sample record (do NOT log all 30)
        Map<String, Object> firstItem = apiList.get(0);
        extentTest.info("Sample Record:");
        extentTest.info("ID: " + firstItem.get("id"));
        extentTest.info("Name: " + firstItem.get("name"));
        extentTest.info("Location: " + firstItem.get("location"));

        extentTest.pass("Tracking Info API validated successfully");
    }

    //  Negative test: missing API key
    @Test
    public void verifyApiFailsWithoutApiKey() {

        ExtentTest extentTest = ExtentTestListener.getTest();

        Response response = RestAssured
                .given()
                .get("https://dev.nebarex.com/file/tracking/tracking-info");

        int statusCode = response.getStatusCode();
        extentTest.info("Status code without API key: " + statusCode);

        Assert.assertTrue(
                statusCode == 401 || statusCode == 403,
                "API should reject request without API key"
        );

        extentTest.pass("API correctly rejects request without API key");
    }
}
